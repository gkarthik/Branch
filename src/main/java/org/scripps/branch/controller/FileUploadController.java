package org.scripps.branch.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.scripps.branch.entity.Collection;
import org.scripps.branch.entity.Dataset;
import org.scripps.branch.entity.User;
import org.scripps.branch.entity.Weka;
import org.scripps.branch.globalentity.DatasetMap;
import org.scripps.branch.repository.CollectionRepository;
import org.scripps.branch.repository.DatasetRepository;
import org.scripps.branch.repository.UserRepository;
import org.scripps.branch.service.AttributeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instances;

@Controller
public class FileUploadController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FileUploadController.class);

	private static final String UPLOAD = "user/uploadMultiple";

	@Autowired
	AttributeService attrSer;
	@Autowired
	CollectionRepository colRepo;
	@Autowired
	DatasetRepository dataRepo;
	@Autowired
	private Job job;
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	UserRepository userRepo;
	@Autowired
	DatasetMap wekaobj;
	@Autowired
	ApplicationContext ctx;
	@Autowired
	String uploadPath;

	private boolean checkArff(String inputfile) {

		int count = 0;
		try {
			LOGGER.debug(inputfile);
			LOGGER.debug("Entered");
			BufferedReader br = new BufferedReader(new FileReader(inputfile));
			String s = null;
			LOGGER.debug("While");
			while ((s = br.readLine()) != null && (s = s.trim()).length() > 0
					&& count < 5) {
				count++;
				if(s.startsWith("%")){
					br.readLine();
				}
				String f[] = s.split(" ");
				LOGGER.debug("F[0]" + f[0]);
				if (f[0].equals("@relation")) {

					return true;
				}

			}
			br.close();
		} catch (IOException ex) {
			LOGGER.debug("ERROR with file" + ex);
		}
		return false;
	}

	public String hashFileName(String name) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(name.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	private String runFeatureUpload(String filePath) {
		JobParameters jp = new JobParametersBuilder().addString("inputPath",
				filePath).toJobParameters();
		try {
			JobExecution jobExecution = jobLauncher.run(job, jp);
			return "Feature table added";
		} catch (JobExecutionAlreadyRunningException | JobRestartException
				| JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			e.printStackTrace();
		}

		return "Unable to add feature table";
	}


	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String uploadMultipleFileHandler(
			@RequestParam(value = "user_id", required = false) Long user_id,
			@RequestParam("file") MultipartFile[] files,
			@RequestParam("description") String description,
			@RequestParam("datasetName") String datasetName,
			@RequestParam("collectionId") long collectionId,
			@RequestParam("fileType") String fileExt, WebRequest req) {

		Weka weka = new Weka();
		Collection col = colRepo.findById(collectionId);
		String message = "";
		UserDetails userDetails = null;
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Dataset ds = new Dataset();
		User user = null;
		String[] fileType = new String[3];
		String privateSet = "";
		boolean exception = false;

		if (req.getParameter("private") != null) {
			privateSet = req.getParameter("private");
		}

		fileType[0] = "dataset";
		fileType[1] = "mapping";
		fileType[2] = "feature";

		String[] names = new String[3];

				if (!(auth instanceof AnonymousAuthenticationToken)) {
					userDetails = (UserDetails) auth.getPrincipal();
					user = userRepo.findByEmail(userDetails.getUsername());
				}



		if (privateSet.equals("1"))
			ds.setPrivateset(true);
		else
			ds.setPrivateset(false);
		ds.setCollection(col);
		ds = dataRepo.saveAndFlush(ds);
		String[] md5FileName = new String[3];

		File serverFile = null;
		Boolean check = false;

		LOGGER.debug("Dataset File type: "+files[0].getContentType());
		//required Validations at Front End for uploads
		if(!( files[0].getContentType().equals("text/plain")||
				files[0].getContentType().equals("application/octet-stream")||
				files[0].getContentType().equals("text/csv"))){
			return "Only ARFF or TXT or CSV file types are allowed for Datasets";
		}


		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String name = file.getOriginalFilename();
			names[i] = file.getOriginalFilename();
			LOGGER.debug("File Name: " + name);
			try {
				byte[] bytes = file.getBytes();
				File dir = new File(uploadPath);

				if (!dir.exists()) {
					dir.mkdirs();
				}

			
				md5FileName[i] = hashFileName(name + user.getId()
						+ System.currentTimeMillis() + fileType[i]);
				LOGGER.debug("MD5 File Name: " + md5FileName[i]);
				serverFile = new File(dir.getAbsolutePath() + File.separator
						+ md5FileName[i]);
				LOGGER.debug("MD5 FileName with path" + serverFile);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				message = message + "You successfully uploaded file, " + name;

				if(i==2)
				LOGGER.debug("Mapping File="+files[2]+"MD5 NaME="+md5FileName[2]+"ServerPath="+serverFile);


//				if (i == 0) {
//					InputStream path1 = ctx.getResource("file:" +
//							serverFile).getInputStream();
//					InputStream path2 = null;
//					if(col.getDatasets().size()>1){
//						path2 = new
//								FileInputStream(uploadPath+col.getDatasets().get(0).getDatasetfile());
//					} else {
//						check = true;
//					}
//					if (path2 != null && path1 != null && check==false) {
//						check = weka.checkDataset(path1, path2);
//					}
//					if (check == false) {
//						serverFile.delete();
//						return
//								"Dataset header does not match any other in collection";
//					}
//				}
//				if (i == 1) {
//					message = message +
//							runFeatureUpload(serverFile.toString());
//				}

//				if (i == 2) {
//
//					LOGGER.debug("File Name:" + serverFile.toString());
//					LOGGER.debug("File Extention" + fileExt);
//
//					String datasetFile = md5FileName[0];
//					Instances data;
//					// boolean success = false;
//
//					try {
//						if (checkArff(uploadPath + datasetFile) == true) {
//							LOGGER.debug("file:" + serverFile.toString());
//							weka.buildWeka(
//									ctx.getResource(
//											"file:" + uploadPath
//											+ md5FileName[0])
//											.getInputStream(), null, "");
//							attrSer.generateAttributesFromDataset(
//									weka.getTrain(), ds, serverFile.toString());
//
//
//							// message = message + "attribute file added";
//
//							message = message + "\nFile Type is ARFF\n"
//									+ "\nAttribute file added\n";
//							exception = false;
//						} 
//						else {
//
//							LOGGER.debug("returned true");
//							data = weka.load(uploadPath + datasetFile);
//							datasetFile = md5FileName[0] + ".csv";
//							weka.toArff(data, uploadPath + datasetFile);
//							attrSer.generateAttributesFromDataset(data,
//									ds, serverFile.toString());
//							LOGGER.debug("Number of Attributes are : "
//									+ data.numAttributes());
//							LOGGER.debug("Number of Instances are : "
//									+ data.numInstances());
//							data.setClassIndex(data.numAttributes() - 1);
//							LOGGER.debug("hmmm");
//							//							Classifier c = new NaiveBayes();
//							//							c.buildClassifier(data);
//							//							LOGGER.debug("C"+c);
//							//							Evaluation eval = new Evaluation(data);
//							//							eval.evaluateModel(c, data);
//							//							LOGGER.debug("Eval"+eval);
//
//							message = message + "\nFile Type is CSV\n"
//									+ "\nAttribute file added\n"
//									;
//							LOGGER.debug(message);
//							exception = false;
//						}
//					} catch (Exception e) {
//
//						LOGGER.error("File Uploaded is not right"
//								, e);
//						exception = true;
//					}
//
//				}

			} catch (Exception e) {
				exception = true;
				LOGGER.error("Exception",e);
			}
		}


		if (!(auth instanceof AnonymousAuthenticationToken && check == true)
				&& exception == false) {

			userDetails = (UserDetails) auth.getPrincipal();
			user = userRepo.findByEmail(userDetails.getUsername());


			for (int i = 0; i < files.length; i++) {
				//			MultipartFile file = files[i];
				names[i] = files[i].getOriginalFilename();
			}

			ds.setDatasetname(names[0]);
			ds.setMappingname(names[1]);
			ds.setFeaturename(names[2]);
			ds.setDescription(description);
			ds.setName(datasetName);

			ds.setDatasetfile(md5FileName[0]);
			ds.setMappingfile(md5FileName[1]);
			ds.setFeaturefile(md5FileName[2]);
			ds = dataRepo.saveAndFlush(ds);
			LOGGER.debug("Success");

		} else {
			LOGGER.debug("Deleted");
			dataRepo.delete(ds);
			return "Invalid File";
		}

		return message;// "redirect:/collection?user_id="+user_id;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String uploadMultipleFileHandler(WebRequest request, Model model) {
		LOGGER.debug("Rendering Multiple upload page");
		return UPLOAD;
	}
}

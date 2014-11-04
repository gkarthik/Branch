package org.scripps.branch.utilities;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.chart.Chart;
import javafx.scene.chart.ChartBuilder;

import org.apache.commons.math3.distribution.HypergeometricDistribution;
import org.apache.commons.math3.random.EmpiricalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.stat.inference.ChiSquareTest;
import org.apache.commons.math3.stat.inference.GTest;
import org.scripps.FisherExact;

/**
 * @author bgood
 *
 */
public class StatUtil {

	public static void buildROC(String[] data) {
		// data[0] = the rank of the row according to the chosen parameter (for
		// example, the yahoo score for a triple)
		// data[1] =
	}

	/***
	 * Given an array of counts, test whether the counts follow a uniform
	 * distribution Test if the dice are loaded...
	 * 
	 * @param data
	 * @return
	 */
	public static double[] chiSquaredTestForUniformDistribution(long[] data) {
		double[] r_p = new double[2]; // r-squared value and the p value
		double[] expected = new double[data.length];
		for (int i = 0; i < expected.length; i++) {
			expected[i] = 1 / (double) expected.length;
		}
		ChiSquareTest test = new ChiSquareTest();
		double chisquare = test.chiSquare(expected, data);
		double p = test.chiSquareTest(expected, data);
		r_p[0] = chisquare;
		r_p[1] = p;
		return r_p;
	}

	public static double chiSquareTest(int plusplus, int minusplus,
			int plusminus, int minusminus) {
		long[][] counts = new long[2][2];
		// ++
		counts[0][0] = plusplus;
		// -+
		counts[1][0] = minusplus;
		// +-
		counts[0][1] = plusminus;
		// --
		counts[1][1] = minusminus;
		ChiSquareTest c = new ChiSquareTest();
		double chi = 0;
		try {
			chi = c.chiSquareTest(counts);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chi;

	}

	public static double chiSquareValue(int plusplus, int minusplus,
			int plusminus, int minusminus) {
		long[][] counts = new long[2][2];
		// ++
		counts[0][0] = plusplus;
		// -+
		counts[1][0] = minusplus;
		// +-
		counts[0][1] = plusminus;
		// --
		counts[1][1] = minusminus;
		ChiSquareTest c = new ChiSquareTest();
		double chi = 0;
		chi = c.chiSquare(counts);

		return chi;

	}

	public static void cleanTrailingTab(String input, String output)
			throws IOException {
		BufferedReader f = new BufferedReader(new FileReader(input));
		String line = f.readLine();
		FileWriter w = new FileWriter(output);
		while (line != null) {
			line = line.trim();
			if (line.endsWith("\t")) {
				line = line.substring(0, line.length() - 1);
			}
			w.write(line + "\n");
			line = f.readLine();
		}
		f.close();
		w.close();
	}

	public static double fishersExact2tailed(int a, int b, int c, int d) {
		// double p =
		// (MathUtils.factorial(a+b)*MathUtils.factorial(c+d)*MathUtils.factorial(a+c)*MathUtils.factorial(b+d))/(MathUtils.factorial(a)*MathUtils.factorial(b)*MathUtils.factorial(c)*MathUtils.factorial(d)*MathUtils.factorial(a+b+c+d));
		double p = 0;
		FisherExact fact = new FisherExact(a + b + c + d);
		p = fact.getTwoTailedP(a, b, c, d);
		return p;

	}

	public static long[] getRandomCountArray(int bins, int max)
			throws Exception {
		Random rnd = new Random(new Date().getTime());
		long[] data = new long[bins];
		for (int i = 0; i < bins; i++) {
			long val = rnd.nextInt(max);
			data[i] = val;
		}
		return data;
	}

	public static double[] getRandomZeroOneArray(int tests, double center)
			throws Exception {
		Random rnd = new Random(new Date().getTime());
		double[] data = new double[tests];
		float x = 1;
		for (int i = 0; i < tests; i++) {
			float val = rnd.nextFloat();
			if (rnd.nextDouble() < 0.5) {
				val = (float) (center + x * val);
			}
			x = x * -1;
			data[i] = val;
		}
		return data;
	}

	public static double getSimpleP(int n_obs, int n_occ, double base_prob) {
		double p = 1;
		int runs = 10000;
		int n_sim_occ = 0;
		for (int r = 0; r < runs; r++) {
			int sim_occ = 0;
			for (int o = 0; o < n_obs; o++) {
				double test = Math.random();
				if (test < base_prob) {
					sim_occ++;
				}
			}
			if (sim_occ > n_occ) {
				n_sim_occ++;
			}
		}
		p = (double) n_sim_occ / runs;
		return p;
	}

	public static String getTabHeader() {
		String header = "N\tsum\tmin\tmax\tmean\tstd dev\tmedian\tskewness\tkurtosis\t";
		return header;
	}

	/***
	 * Given an array of counts, test weather the counts follow a uniform
	 * distribution Test if the dice are loaded... G tests are apparently more
	 * reliable for smaller amounts of data
	 * 
	 * @param data
	 * @return
	 */
	public static double[] gTestForUniformDistribution(long[] data) {
		double[] r_p = new double[2]; // r-squared value and the p value
		double[] expected = new double[data.length];
		boolean zero_vals = false;
		for (int i = 0; i < expected.length; i++) {
			if (data[i] == 0) {
				zero_vals = true;
			}
			expected[i] = 1 / (double) expected.length;
		}
		if (zero_vals) {
			// System.out.println("Adding one to all bins to get rid of zero values.. might not be appropiate!");
			for (int i = 0; i < expected.length; i++) {
				data[i] = data[i] + 1;
			}
		}
		GTest test = new GTest();
		double g = test.g(expected, data);
		double p = test.gTest(expected, data);
		r_p[0] = g;
		r_p[1] = p;
		return r_p;
	}

	public static double hypergeoTest(int populationSize,
			int numberOfSuccessesInPopulation, int sampleSize,
			int successesInSample) {
		HypergeometricDistribution h = new HypergeometricDistribution(
				populationSize, numberOfSuccessesInPopulation, sampleSize);
		return h.probability(successesInSample);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// Map<String,double[]> datas = new HashMap<String, double[]>();
		// datas.put("on10", getRandomZeroOneArray(2000, 10));
		// datas.put("on1", getRandomZeroOneArray(2000, 1));
		//
		// int bin_count = 3;
		// String title = "Histo1";
		// String xAxisTitle = "Bin Means";
		// String yAxisTitle = "Counts";
		// String file_out = null;
		// plotHistograms(datas, bin_count, title, xAxisTitle, yAxisTitle,
		// file_out, true);

		// data = {8, 1, 1, 0, 2, 5, 3, 3, 1, 2, 3, 1, 3, 1, 2, 2, 0, 5, 3, 2,
		// 2, 3, 0, 2, 0};
		// //{11, 2, 1, 1, 4, 5, 3, 4, 2, 2, 4, 3, 3, 3, 2, 3, 1, 6, 2, 4, 2, 4,
		// 0, 4, 0};
		// long[] data = {44,56};
		// double[] r_p = chiSquaredTestForUniformDistribution(data);
		// System.out.println("chisquared: "+r_p[0]+"\tP: "+r_p[1]);
		// r_p = gTestForUniformDistribution(data);
		// System.out.println("G: "+r_p[0]+"\tP: "+r_p[1]);

		// transposeMatrix("/Users/bgood/data/branch/kidney_transplant_rejection/Kunil_GSE12187_processed_CBF_023.txt","/Users/bgood/data/branch/kidney_transplant_rejection/Kunil_GSE12187_transposed_CBF_023.txt");
		transposeMatrix("/home/sulab/3.txt",
				"/home/sulab/branch_griffith_tmp_trans.txt");
		// cleanTrailingTab("/Users/bgood/Desktop/branch_griffith.txt",
		// "/Users/bgood/Desktop/branch_griffith_tmp_trans.txt");
	}

	public static double oddsRatio(int a, int b, int c, int d) {
		double o = (a / (a + c)) / (b / (b + d));
		return o;
	}

	/**
	 * Given an array of doubles, create and display a histogram. bin_count can
	 * be set automatically if zero. file_output is optional - set to null to
	 * not write anything - same for dataTitle which corresponds to legend info.
	 * 
	 * @param data
	 * @param bin_count
	 * @param title
	 * @param xAxisTitle
	 * @param yAxisTitle
	 * @param dataTitle
	 * @param file_out
	 */
	public static void plotHistograms(Map<String, double[]> datas,
			int bin_count, String title, String xAxisTitle, String yAxisTitle,
			String file_out, boolean freq_scale) {
		double[] data1 = datas.values().iterator().next();
		if (bin_count == 0) {
			bin_count = (int) Math.sqrt(data1.length);
			// or Sturges like in R
			// bin_count = (int)
			// ((Math.log(data.length)/Math.log(2))*data.length)+1;
			// //or Rice rule
			// bin_count = (int) (2*Math.pow(bin_count, 1/3));
			// lots more available here
			// https://en.wikipedia.org/wiki/Histogram#Number_of_bins_and_width
			// and this sounds great if we could get it working
			// http://toyoizumilab.brain.riken.jp/hideaki/res/histogram.html
		}

		// initialize chart
		// following http://xeiam.com/xchart_examplecode.jsp
		Chart chart = new ChartBuilder().chartType(ChartType.Bar).width(1100)
				.height(600).title(title).xAxisTitle(xAxisTitle)
				.yAxisTitle(yAxisTitle).build();
		chart.getStyleManager().setAxisTickLabelsFont(
				new Font(Font.SERIF, Font.PLAIN, 10));
		chart.getStyleManager().setNormalDecimalPattern("#0.00");

		// get the data into the chart
		for (String dataTitle : datas.keySet()) {
			double[] dataD = datas.get(dataTitle);
			double[] bins = new double[bin_count];
			double[] counts = new double[bin_count];
			double[] freqs = new double[bin_count];
			EmpiricalDistribution distribution = new EmpiricalDistribution(
					bin_count);
			distribution.load(dataD);
			double n = distribution.getSampleStats().getN();
			int k = 0;

			for (SummaryStatistics stats : distribution.getBinStats()) {
				double mean = stats.getMean();
				if (Double.isNaN(mean)) {
					mean = 0;
				}
				bins[k] = mean;
				freqs[k] = stats.getN() / n;
				counts[k] = stats.getN();
				k++;
			}

			// add dataseries to chart
			if (freq_scale) {
				List<String> labels = new ArrayList<String>();
				// add the raw counts to the labels on the x axis
				for (int i = 0; i < bins.length; i++) {
					double bin = bins[i];
					int count = (int) counts[i];
					double rounded = (double) Math.round(bin * 100) / 100;
					String label = rounded + ""; // ("+count+")
					labels.add(label);
				}
				ArrayList<Number> freqs_ = new ArrayList<Number>();
				for (double d : freqs) {
					freqs_.add(d);
				}
				chart.addCategorySeries(dataTitle, labels, freqs_);
			} else {
				chart.addSeries(dataTitle, bins, counts);
			}
		}
		// Show it
		new SwingWrapper(chart).displayChart();

		// save it
		if (file_out != null) {
			try {
				BitmapEncoder.savePNG(chart, file_out + ".png");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String statsToTabString(DescriptiveStatistics stats) {
		String row = stats.getN() + "\t" + stats.getSum() + "\t"
				+ stats.getMin() + "\t" + stats.getMax() + "\t"
				+ stats.getMean() + "\t" + stats.getStandardDeviation() + "\t"
				+ stats.getPercentile(50) + "\t" + stats.getSkewness() + "\t"
				+ stats.getKurtosis() + "\t";
		return row;
	}

	public static void transposeMatrix(String input, String output)
			throws IOException {
		BufferedReader f;
		f = new BufferedReader(new FileReader(input));
		String line = f.readLine();
		List<String[]> data = new ArrayList<String[]>();
		String[] items = line.split("\t");
		int width = items.length;
		while (line != null) {
			items = line.split("\t");
			data.add(items);
			line = f.readLine();
		}
		f.close();
		System.out.println("input read, writing " + data.size() + " by "
				+ width);
		FileWriter w = new FileWriter(output);
		// read 54683 , 54682
		for (int i = 0; i < width; i++) {
			for (int r = 0; r < data.size(); r++) {
				String[] row = data.get(r);
				w.write(row[i]);
				if (r < data.size() - 1) {
					w.write("\t");
				}
			}
			w.write("\n");
		}
		w.close();
	}

}
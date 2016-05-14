package maelstrom.math;

public class RandomGenerator {

  /**
   * Generates a Poisson-distributed random variable
   */
  public static int getPoisson(double lambda) {
    double L = Math.exp(-lambda);
    double p = 1.0;
    int k = 0;

    do {
      k++;
      p *= Math.random();
    } while (p > L);

    return k - 1;
  }
}

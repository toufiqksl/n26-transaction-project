package com.n26.entity;

public class StatisticsResult {
    private double sum = 0.00;
    private double avg;
    private double max = Double.MIN_VALUE;
    private double min = Double.MAX_VALUE;
    private long count = 0l;

    public StatisticsResult() {
    }

    public StatisticsResult(Statistics statistics) {
        this.sum = statistics.getSum();
        this.count = statistics.getCount();
        this.max = statistics.getMax();
        this.min = statistics.getMin();
    }

    public double getSum() { 
    	return Math.round(sum*100.0)/100.0;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getAvg() {
    	return Math.round(avg*100.0)/100.0;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMax() {
    	return Math.round(max*100.0)/100.0;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
    	return Math.round(min*100.0)/100.0;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatisticsResult summary = (StatisticsResult) o;

        if (Double.compare(summary.sum, sum) != 0) return false;
        if (count != summary.count) return false;
        if (Double.compare(summary.max, max) != 0) return false;
        if (Double.compare(summary.min, min) != 0) return false;
        return Double.compare(summary.avg, avg) == 0;

    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(sum);
        int result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (count ^ (count >>> 32));

        temp = Double.doubleToLongBits(max);
        result = 31 * result + (int) (temp ^ (temp >>> 32));

        temp = Double.doubleToLongBits(min);
        result = 31 * result + (int) (temp ^ (temp >>> 32));

        temp = Double.doubleToLongBits(avg);
        result = 31 * result + (int) (temp ^ (temp >>> 32));

        return result;
    }

    @Override
    public String toString() {
        return "StatisticsSummary{" +
                "sum=" + sum +
                ", avg=" + avg +
                ", max=" + max +
                ", min=" + min +
                ", count=" + count +
                '}';
    }
}

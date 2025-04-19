package lib;

public class TaxFunction {
	private static final int taxRatePercent = 5;
	private static final int baseTaxFreeIncome = 54000000;
	private static final int spouseTaxFreeIncome = 4500000;
	private static final int childTaxFreeIncome = 4500000; // Disesuaikan berdasarkan komentar
	private static final int maxChildrenForTaxDeduction = 3;

	/**
	 * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus dibayarkan setahun.
	 * 
	 * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan) dikurangi penghasilan tidak kena pajak.
	 * 
	 * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena pajaknya adalah Rp 54.000.000.
	 * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000.
	 * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
	 */
	public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean isMarried, int numberOfChildren) {
		
		validateInput(numberOfMonthWorking, numberOfChildren);
		
		// Hitung penghasilan tahunan kotor
		int annualIncome = (monthlySalary + otherMonthlyIncome) * numberOfMonthWorking;
		
		// Hitung penghasilan tidak kena pajak
		int taxFreeIncome = calculateTaxFreeIncome(isMarried, numberOfChildren);
		
		// Hitung penghasilan kena pajak
		int taxableIncome = annualIncome - deductible - taxFreeIncome;
		
		// Hitung pajak (5% dari penghasilan kena pajak) atau 0 jika penghasilan kena pajak negatif
		int tax = Math.max(0, (int) Math.round(taxRatePercent / 100.0 * taxableIncome));
		
		return tax;
	}
	
	private static void validateInput(int numberOfMonthWorking, int numberOfChildren) {
		if (numberOfMonthWorking > 12) {
			System.err.println("More than 12 month working per year");
		}
	}
	
	private static int calculateTaxFreeIncome(boolean isMarried, int numberOfChildren) {
		int taxFreeIncome = baseTaxFreeIncome;
		
		if (isMarried) {
			taxFreeIncome += spouseTaxFreeIncome;
		}
		
		int childrenForDeduction = Math.min(numberOfChildren, maxChildrenForTaxDeduction);
		taxFreeIncome += childrenForDeduction * childTaxFreeIncome;
		
		return taxFreeIncome;
	}
}
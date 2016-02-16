package id.tech.rcshospital;

public class RowDataMedicalHistory {
	
	public String mId, mNamaDok, mBagian, mTgl, mDiagnosa, mPenanganan;

	public RowDataMedicalHistory(final String id, final String namaDok,
			final String bagian, final String tgl, final String diagnosa, final String penanganan) {
		mId = id;
		mNamaDok = namaDok;
		mBagian = bagian;
		mTgl = tgl;
		mDiagnosa = diagnosa;
		mPenanganan = penanganan;
	}

}

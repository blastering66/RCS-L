package id.tech.rcshospital;

public class RowDataPrevKonsultasi {
	
	public String mId, mNamaDok, mKeluhan, mTgl, mDiagnosa, mPenanganan;

	public RowDataPrevKonsultasi(final String id, final String namaDok,
			final String keluhan, final String tgl, final String diagnosa, final String penanganan) {
		mId = id;
		mNamaDok = namaDok;
		mKeluhan = keluhan;
		mTgl = tgl;
		mDiagnosa = diagnosa;
		mPenanganan = penanganan;
	}

}

package id.tech.rcshospital;

public class RowDataAppTracker {
	public String mId, mKeperluan, mKeterangan, mTgl, mJam, mNamaDokter;
	
	public RowDataAppTracker(final String id, final String keperluan,
			final String keterangan, final String tgl, final String jam, final String dokter) {
		mId = id;
		mKeperluan = keperluan;
		mKeterangan = keterangan;
		mTgl = tgl;
		mJam = jam;
		mNamaDokter = dokter;
	}

}

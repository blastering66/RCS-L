package id.tech.rcshospital;

public class RowDataHospitalInfo {
	public String mId, mNamaCabang, mAlamat, mTlpCS, mTlpUGD, mLat, mLong;
	
	public RowDataHospitalInfo(final String id,final String nama, final String alamat, final String cs, final String ugd,
			final String lat, final String longi) {
		// TODO Auto-generated constructor stub
		mId = id;
		mNamaCabang = nama;
		mAlamat = alamat;
		mTlpCS = cs;
		mTlpUGD = ugd;
		mLat = lat;
		mLong = longi;
	}

}

package id.tech.rcshospital;

import java.io.Serializable;
import java.util.List;

public class RowDataDoctor {
	public String mIdDoc, mNamaDoc, mSpesialisasi, mUrlGambar;
	public String mIdJadwal, mHariJam, mIdCabang, mNamaCabang;
	public List<RowDataDoctorJadwal> mDataJadwal;
	
	public RowDataDoctor(final String id, final String namaDoc, final String spesialisasi, final String urlGambar){
		this.mIdDoc = id;
		this.mNamaDoc = namaDoc;
		this.mSpesialisasi = spesialisasi;
		this.mUrlGambar = urlGambar;
	}
	
	public RowDataDoctor(final String id, final String namaDoc, final String spesialisasi, final String urlGambar, final List<RowDataDoctorJadwal> dataJadwal){
		this.mIdDoc = id;
		this.mNamaDoc = namaDoc;
		this.mSpesialisasi = spesialisasi;
		this.mUrlGambar = urlGambar;
		this.mDataJadwal = dataJadwal;
	}
	
	public RowDataDoctor(final String idDoctor, final String idJadwal, final String hariJam, final String idCabang, final String namaCabang){
		mIdDoc = idDoctor;
		mIdJadwal = idJadwal;
		mHariJam = hariJam;
		mIdCabang = idCabang;
		mNamaCabang = namaCabang;
	}
	
	

}

package id.tech.util;

import java.util.List;

import id.tech.rcshospital.RowDataDoctor;
import id.tech.rcshospital.RowDataDoctorJadwal;

public class ParameterCollection {
	public static String TAG_TEST="Test";
	public static int RESULT_REQUEST_CODE= 3003;
	public static List<RowDataDoctor> data = null;
	
	public static final String URL_GETREM = "http://128.199.176.5/lafiya/json/json_reminder.php?";
	public static final String URL_GETMEDHISTORY = "http://128.199.176.5/lafiya/json/json_medicalhistory.php?";
	public static final String URL_GETAPPOINTMENT = "http://128.199.176.5/lafiya/json/json_getappointment.php?";
	public static final String URL_GETDOCTORINFO = "http://128.199.176.5/lafiya/json/json_doctor.php?";
	public static final String URL_GETALLDOCTOR = "http://128.199.176.5/lafiya/json/json_doctor.php";
	public static final String URL_HOSPITALINFO = "http://128.199.176.5/lafiya/json/json_infors.php";
	public static final String URL_POST_APPOINMENT = "http://128.199.176.5/lafiya/json/json_newappointment.php?";
	public static final String URL_LOGIN = "http://128.199.176.5/lafiya/json/json_login.php?";
	public static final String URL_REGISTER_PASIEN = "http://128.199.176.5/lafiya/json/json_newuser.php?";
	public static final String URL_CHANGE_PASS = "http://128.199.176.5/lafiya/json/json_changepassword.php?";
	
	public static final String SHARED_PREF_NAME = "hospital";

	public static final String TAG_SOF = "SOF";
	public static final String TAG_SOF_MESSAGE = "SOFMessage";

	// REMINDER API
	public static final String TAG_REMINDER = "reminder";
	public static final String TAG_IDREM = "idRem";
	public static final String TAG_IDPAS = "idPas";
	public static final String TAG_JENISREM = "jenisRem";
	public static final String TAG_OBAT = "obat";
	// Arraydlm OBAT
	public static final String TAG_OBAT_INFOOBAT = "infoobat";
	public static final String TAG_OBAT_INFOOBAT_IDOBAT = "idObat";
	public static final String TAG_OBAT_INFOOBAT_NAMAOBAT = "namaObat";
	public static final String TAG_OBAT_KET = "keterangan";
	public static final String TAG_OBAT_TGLBELI = "tglbeli";
	// Array dlm jenis Reminder Vaksin
	public static final String TAG_VAKSIN = "vaksin";
	public static final String TAG_VAKSIN_INFOVAKSIN = "infovaksin";
	public static final String TAG_VAKSIN_INFOVAKSIN_IDVAKSIN = "idvaksin";
	public static final String TAG_VAKSIN_INFOVAKSIN_NAMAVAKSIN = "namavaksin";
	public static final String TAG_VAKSIN_INFOVAKSIN_KETERANGAN = "keterangan";
	public static final String TAG_VAKSIN_TGLVAKSIN = "tglvaksin";

	public static final String TAG_TGLREM = "tglRem";
	public static final String TAG_PESAN = "pesan";
	public static final String TAG_CREATEDDATE = "createdDate";
	public static final String TAG_CREATEDBY = "createdBy";
	public static final String TAG_UPDATEDDATE = "updatedDate";
	public static final String TAG_UPDATEDBY = "updatedBy";

	// MEDICAL HISTORY
	public static final String TAG_MEDHISTORY = "medicalhistory";
	public static final String TAG_MEDHISTORY_ID = "idMedHis";
	public static final String TAG_MEDHISTORY_DOCTOR = "dokter";
	public static final String TAG_MEDHISTORY_IDDOC = "idDokter";
	public static final String TAG_MEDHISTORY_NAMADOC = "namaDokter";
	public static final String TAG_MEDHISTORY_SPESIALISASI = "spesialisasi";
	public static final String TAG_MEDHISTORY_IDPASIEN = "idPasien";
	public static final String TAG_MEDHISTORY_TGLKONSUL = "tglKonsultasi";
	public static final String TAG_MEDHISTORY_KESIMPULAN = "kesimpulan";
	public static final String TAG_MEDHISTORY_PENANG = "penanganan";
	public static final String TAG_MEDHISTORY_OBAT = "obat";

	// APPOINTMENT
	public static final String TAG_APPOINMENT = "appointment";
	public static final String TAG_APPOINMENT_ID = "idAppointment";
	public static final String TAG_APPOINMENT_TGL = "tglAppointment";
	public static final String TAG_APPOINMENT_JAM = "jamAppointment";

	public static final String TAG_APPOINMENT_DOCTOR = "dokter";
	public static final String TAG_APPOINMENT_DOCTOR_IDDOC = "idDokter";
	public static final String TAG_APPOINMENT_DOCTOR_NAMADOC = "namaDokter";

	public static final String TAG_APPOINMENT_PASIEN = "pasien";
	public static final String TAG_APPOINMENT_PASIEN_IDPASIEN = "idPasien";
	public static final String TAG_APPOINMENT_PASIEN_NAMAPASIEN = "namaPasien";

	public static final String TAG_APPOINMENT_KEPERLUAN = "keperluan";
	public static final String TAG_APPOINMENT_KETERANGAN = "keterangan";

	// DOCTOR
	public static final String TAG_DOCTOR = "doctor";
	public static final String TAG_DOCTOR_IDDOC = "idDokter";
	public static final String TAG_DOCTOR_NAMADOC = "namDokter";
	public static final String TAG_DOCTOR_SPESIALISASI = "spesialisasi";
	public static final String TAG_DOCTOR_PHOTO = "photo";

	public static final String TAG_DOCTOR_JADWAL = "jadwal";
	public static final String TAG_DOCTOR_JADWAL_ID = "idJadwal";
	public static final String TAG_DOCTOR_JADWAL_WAKTU = "haridanjam";

	public static final String TAG_DOCTOR_JADWAL_CABANG = "cabang";
	public static final String TAG_DOCTOR_JADWAL_CABANG_ID = "idCabang";
	public static final String TAG_DOCTOR_JADWAL_CABANG_NAMA = "namaCabang";

	// HOSPITAL INFO
	public static final String TAG_CABANG = "cabang";
	public static final String TAG_CABANG_ID = "idCabang";
	public static final String TAG_CABANG_NAMA = "namaCabang";
	public static final String TAG_CABANG_ALAMAT = "alamat";
	public static final String TAG_CABANG_TLPCS = "tlpCS";
	public static final String TAG_CABANG_TLPUGD = "tlpUGD";
	public static final String TAG_CABANG_LAT = "lat";
	public static final String TAG_CABANG_LONG = "long";
	
	//LOGIN
	public static final String TAG_LOGIN_ITEM = "user";
	public static final String TAG_LOGIN_USER = "username";
	public static final String TAG_LOGIN_PASS = "password";
	public static final String TAG_LOGIN_IDPASIEN = "idPasien";

}


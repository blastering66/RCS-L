package id.tech.rcshospital;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableDataMedicalHistory implements Parcelable{
	private List<RowDataMedicalHistory> data;
	
	private ParcelableDataMedicalHistory(List<RowDataMedicalHistory> data){
		this.data = data;
	}	
	
	private ParcelableDataMedicalHistory(Parcel in){
//			this.data = in.readList(data, null);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeList(data);
	}
	
	private void readFromParcel(Parcel in){
//		data = in.readArrayList(loader)
	}
	
	public static final Creator CREATOR = new Creator() {
		@Override
		public Object createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new ParcelableDataMedicalHistory(source);
		}
		
		@Override
		public Object[] newArray(int size) {
			// TODO Auto-generated method stub
			return new ParcelableDataMedicalHistory[size];
		}
	};

}

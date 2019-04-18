package gldw.citizen;

import vdab.api.event.VDABData;
import vdab.api.node.JavaFunctionSet_A;

public class TDSInputSupport  extends JavaFunctionSet_A {

	private final static String PATH_LOCATIONID = ".LocationID";
	private final static String PATH_USERID = ".UserID";
	private final static String PATH_COLLECTIONDATE = ".CollectionDate";
	private final static String PATH_LATITUDE = ".Latitude";
	private final static String PATH_LONGITUDE = ".Longitude";
	private final static String PATH_DEPTH = ".Depth";	
	private final static String PATH_TDS = ".TDS";		
	private final static String PATH_TEMPERATURE = ".Temperature";	
	public VDABData func_validateTDSData(VDABData inData, String basePath) {
		String err = null;

		String locationID  = inData.getDataAsString(basePath + PATH_LOCATIONID);
		err = checkLocationID(locationID);
		if (err != null){
			pushError(err);
			return null;
		}
		
		Long collectionDate = inData.getDataAsLong(basePath + PATH_COLLECTIONDATE);
		err = checkCollectionDate(collectionDate);
		if (err != null){
			pushError(err);
			return null;
		}	
		
		String userID  = inData.getDataAsString(basePath + PATH_USERID);
		err = checkForNull("User ID", userID);
		if (err != null){
			pushError(err);
			return null;
		}
		
		Double latitude = inData.getDataAsDouble(basePath + PATH_LATITUDE);
		err = checkForNull("Latitude", latitude);
		if (err != null){
			pushError(err);
			return null;
		}	

		Double longitude = inData.getDataAsDouble(basePath + PATH_LONGITUDE);
		err = checkForNull("Longitude", longitude);
		if (err != null){
			pushError(err);
			return null;
		}	
		
		Double depth = inData.getDataAsDouble(basePath + PATH_DEPTH);
		err = checkForNull("Depth", depth);
		if (err != null){
			pushError(err);
			return null;
		}	
		
		inData.addDataObject("ReceiptNumber", getReceiptNumber(locationID, collectionDate));
		return inData;	
	}

	private String getReceiptNumber(String locationID, Long date) {
		StringBuilder sb = new StringBuilder();
		sb.append(locationID);
		sb.append("-");
		long remainder = date.longValue() % 10000L;
		sb.append(remainder);
		return sb.toString();
	}
	private String checkForNull(String attrName, Object value){
		if (value == null)
			return "The "+attrName+ " must be specified";
		return null;
	}
	private String checkLocationID(String locationID){
		String err = checkForNull("Location ID", locationID);
		if (err != null)
			return err;

		if (locationID.length() != 8)
			return "The Location ID must contain exactly 8 digits";
			
		return null;
	}
	private String checkCollectionDate(Long collectionDate){
		String err = checkForNull("Collection Date", collectionDate);
		if (err != null)
			return err;

		long now = System.currentTimeMillis();
		if (collectionDate.longValue() > now)
			return "Collection Date can not be in the future";
	
		return null;
	}
}

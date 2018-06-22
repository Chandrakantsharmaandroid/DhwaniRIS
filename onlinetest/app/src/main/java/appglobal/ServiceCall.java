package appglobal;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import appconstent.ConstantClass;

public class ServiceCall {

	private String mServiceMethod;
	private static final int HTTP_STATUS_OK = 200;
	private static final String logTag = "ServiceCall";
	public static final int CONNECTION_TIME_OUT = 500000;
	public static final int SOCKET_TIME_OUT = 800000;
	private static String TAG = "ServiceCall";

	/**
	 * Exception
	 */
	public static class ApiException extends Exception {
		private static final long serialVersionUID = 1L;

		public ApiException(String msg) {
			super(msg);
		}

		public ApiException(String msg, Throwable thr) {
			super(msg, thr);
		}
	}

	/**
	 * ServiceCall Constructor
	 */
	public ServiceCall(Context signUpActivity, String mServiceMethod) {
		super();
		this.mServiceMethod = mServiceMethod;

	}

	public String getResponseFromServer(String url) throws ApiException {

		String finalURL = ConstantClass.COMMAN_URI+url;//"http://141.255.191.143:1021/api/common/getgroup?userId=61";

		Log.d(logTag, "finalURL " + finalURL);

		/** Common Technique to call URL Service */
		String mResponce = getServerResponse(finalURL);
		Log.d(logTag, "just call response " + mResponce);

		if (mResponce != null) {
			return mResponce;
		} else {
			return "";
		}
	}

    public String getResponseFromServerPost(String url, List<NameValuePair> pairs) throws ApiException {

        String finalURL = ConstantClass.COMMAN_URI+url;//"http://141.255.191.143:1021/api/common/getgroup?userId=61";

        Log.d(logTag, "finalURL " + finalURL);

        /** Common Technique to call URL Service */
        String mResponce = getServerResponsePost(finalURL, pairs,"");
        Log.d(logTag, "just call response " + mResponce);

        if (mResponce != null) {
            return mResponce;
        } else {
            return "";
        }
    }

	public String getResponseFromServerPost(String url, List<NameValuePair> pairs, String flag) throws ApiException {

		String finalURL = ConstantClass.COMMAN_URI+url;//"http://141.255.191.143:1021/api/common/getgroup?userId=61";

		Log.d(logTag, "finalURL " + finalURL);

		/** Common Technique to call URL Service */
		String mResponce = getServerResponsePost(finalURL, pairs, flag);
		Log.d(logTag, "just call response " + mResponce);

		if (mResponce != null) {
			return mResponce;
		} else {
			return "";
		}
	}

	protected static synchronized String getServerResponse(String url) throws ApiException {
		String responseVal = null;
		Log.d(logTag, "Fetching " + url);
		// create an http client and a request object.
		final HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), CONNECTION_TIME_OUT); // Timeout Limit
		HttpConnectionParams.setSoTimeout(client.getParams(), SOCKET_TIME_OUT);
		HttpResponse response;
		HttpGet request = new HttpGet(url);
		//StringBuffer stringBuffer = new StringBuffer("");

		try {
			// execute the request
			response = client.execute(request);

			Log.d(logTag, "response " + response);
			StatusLine status = response.getStatusLine();
			/*if (status.getStatusCode() != HTTP_STATUS_OK) {
				// handle error here
				throw new ApiException("Invalid response from last fm" +
						status.toString());
			}*/
			final HttpEntity entity = response.getEntity();

			if (entity != null) {
				// Read the content stream
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();

					// convert content stream to a String
					String resultString = convertStreamToString(inputStream);
					Log.i(TAG, resultString);

					return resultString;
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}


		} catch (Exception e) {
			throw new ApiException("Problem connecting to the server " +
					e.getMessage(), e);
		}

		return responseVal;
	}



	protected static synchronized String getServerResponsePost(String url, List<NameValuePair> pairs, String token) throws ApiException {
		String responseVal = null;
		Log.d(logTag, "Fetching " + url);
		// create an http client and a request object.
		final HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), CONNECTION_TIME_OUT); // Timeout Limit
		HttpConnectionParams.setSoTimeout(client.getParams(), SOCKET_TIME_OUT);
		HttpResponse response;
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("token",token);
		//StringBuffer stringBuffer = new StringBuffer("");

		try {
			/*List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("attendancedata", JSONObject.toString()));*/

			httpPost.setEntity(new UrlEncodedFormEntity(pairs));

			response = client.execute(httpPost);

			Log.d(logTag, "response " + response);
			StatusLine status = response.getStatusLine();
			if (status.getStatusCode() != HTTP_STATUS_OK) {
				// handle error here
				throw new ApiException("Invalid response from last fm" +
						status.toString());
			}
			final HttpEntity entity = response.getEntity();

			if (entity != null) {
				// Read the content stream
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();

					// convert content stream to a String
					String resultString = convertStreamToString(inputStream);
					Log.i(TAG, resultString);

					return resultString;
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}


		} catch (Exception e) {
			throw new ApiException("Problem connecting to the server " +
					e.getMessage(), e);
		}

		return responseVal;
	}


	/**
	 * convert stream to string
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Log.i("Response", sb.toString());
		return sb.toString();
	}

}

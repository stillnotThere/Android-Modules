RestClient client = new RestClient(webServiceUrl);
client.addBasicAuthentication(username, password);
try {
	client.execute(RequestMethod.GET);
	if (client.getResponseCode() != 200) {
		//return server error
		return client.getErrorMessage();
	}
	//return valid data
	JSONObject jObj = new JSONObject(client.getResponse());
	return jObj.toString();
} catch(Exception e) {
	return e.toString();
}
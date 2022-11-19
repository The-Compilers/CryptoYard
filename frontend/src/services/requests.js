// All code for sending requests to backend is stored in this file

import { getCookie } from "./cookies";
import { HttpResponseError } from "./requests/HttpResponseError";

// Import REST API BASE URL from the environment variable, see .env file
// Note: all environment variables must start with REACT_, otherwise React will not handle them!
export const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

/**
 * Send an asynchronous HTTP GET request to the remote API (backend)
 * @param {string} url Relative backend API url
 * @return {Promise<string>} The response text (body) received from the API.
 * @throws {HttpResponseError} Error code and message from the response body
 */
export async function asyncApiGet(url) {
  return asyncApiRequest("GET", url, null);
}

/**
 * Send an asynchronous HTTP POST request to the remote API (backend)
 * @param {string} url Relative backend API url
 * @param requestBody The parameters to include in the request body
 * @return {Promise<string>} The response text (body) received from the API.
 * @throws {HttpResponseError} Error code and message from the response body
 */
export async function asyncApiPost(url, requestBody) {
  return asyncApiRequest("POST", url, requestBody);
}

/**
 * Send an asynchronous HTTP DELETE request to the remote API (backend)
 * @param {string} url Relative backend API url
 * @param requestBody The parameters to include in the request body
 * @return {Promise<string>} The response text (body) received from the API.
 * @throws {HttpResponseError} Error code and message from the response body
 */
export async function asyncApiDelete(url, requestBody) {
  return asyncApiRequest("DELETE", url, requestBody);
}

/**
 * Send and asynchronous request to the remote API.
 * Add the JWT token automatically (if one is available).
 * @param {string} method the HTTP method to use: GET, POST, PUT. Case-insensitive.
 * @param {string} url The relative API url (base URL is added automatically)
 * @param {object} requestBody The data to send in request body. Ignored for HTTP GET.
 * @return {Promise<string>} The response text received from the API.
 * @throws {HttpResponseError} Error code and message from the response body
 */
async function asyncApiRequest(method, url, requestBody) {
  const fullUrl = API_BASE_URL + url;
  let headers = getAuthenticationHeaders();
  let body = null;
  if (method.toLowerCase() !== "get" && requestBody) {
    headers["Content-Type"] = "application/json";
    body = JSON.stringify(requestBody);
  }

  return fetch(fullUrl, {
    method: method,
    mode: "cors",
    headers: headers,
    body: body,
  })
    .then(handleErrors)
    .then((response) => response.text());
}

/**
 * Get HTTP request headers with authentication info, if it is available
 * @return {object} Header object, which will include an "Authorization" header,
 * if JWT token is available.
 */
function getAuthenticationHeaders() {
  let headers = {};
  const jwtToken = getCookie("jwt");
  if (jwtToken) {
    headers["Authorization"] = "Bearer " + jwtToken;
  }
  return headers;
}

/**
 * Check whether the HTTP response has a 200 OK status. If it does, return the
 * response. If it does not, throw an Error
 * @param response
 * @return {response} The response (if all was OK)
 * @throws Error containing the response code and text from the response body
 */
async function handleErrors(response) {
  if (!response.ok) {
    const responseText = await response.text();
    throw new HttpResponseError(response.status, responseText);
  }
  return response;
}

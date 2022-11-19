// All code for sending requests to backend is stored in this file

import { getCookie } from "./cookies";
import { HttpResponseError } from "./requests/HttpResponseError";

// Import REST API BASE URL from the environment variable, see .env file
// Note: all environment variables must start with REACT_, otherwise React will not handle them!
export const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

/**
 * Send an HTTP POST request to the backend
 * @param {string} url relative URL of the API endpoint
 * @param {function} callback Callback function to call on success, with response data (JSON-decoded) as the parameter
 * @param {object} requestBody When supplied, send this data in the request body. Does not work with HTTP GET!
 * @param {function} errorCallback A function called when the response code is not 200. Two parameters will be passed
 * to the function: HTTP response code and response body (as text)
 */
export function sendApiPostRequest(url, callback, requestBody, errorCallback) {
  return sendApiRequest("POST", url, callback, requestBody, errorCallback);
}

/**
 * Send a REST-API request to the backend
 * @param method The method to use: GET, POST, PUT, DELETE
 * @param url relative URL of the API endpoint
 * @param callback Callback function to call on success, with response data (JSON-decoded) as the parameter
 * @param requestBody When supplied, send this data in the request body. Does not work with HTTP GET!
 * @param errorCallback A function called when the response code is not 200. Two parameters will be passed
 * to the function: HTTP response code and response body (as text)
 */
function sendApiRequest(method, url, callback, requestBody, errorCallback) {
  const request = new XMLHttpRequest();
  request.onreadystatechange = function () {
    if (request.readyState === XMLHttpRequest.DONE) {
      if (request.status === 200) {
        let responseBody = "";
        if (request.responseText) {
          try {
            responseBody = JSON.parse(request.responseText);
          } catch (e) {
            responseBody = request.responseText;
          }
        }
        callback(responseBody);
      } else if (errorCallback) {
        errorCallback(request.status, request.responseText);
      } else {
        console.error("Error in API request");
      }
    }
  };
  const fullUrl = API_BASE_URL + url;
  console.log("Sending request to " + fullUrl);
  request.open(method, fullUrl);

  // Set JWT token, if it is stored in a cookie
  const jwtToken = getCookie("jwt");
  if (jwtToken) {
    request.setRequestHeader("Authorization", "Bearer " + jwtToken);
  }

  // Do we need to include data in the request?
  if (requestBody) {
    if (method.toLowerCase() !== "get") {
      request.setRequestHeader("Content-Type", "application/json");
      request.send(JSON.stringify(requestBody));
    } else {
      console.error("Trying to send request data with HTTP GET, not allowed!");
      request.send();
    }
  } else {
    request.send();
  }
}

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

import { asyncApiDelete, asyncApiGet, sendApiPostRequest } from "./requests";
import { getAuthenticatedUser } from "./authentication";

//////////////////////////////////////
// API requests to the backend
//////////////////////////////////////

/**
 * Send request to API - delete user account
 * @param {string} username Username of the user to delete
 * @param {string} password Password of the user to delete
 * @return {Promise<string>} Promise to return the response body as a string
 * Throws and exception on error
 */
export function sendUserDeleteRequest(username, password) {
  const postData = {
    username: username,
    password: password,
  };
  return asyncApiDelete("/close-account", postData);
}

/**
 * Send request to API - save API key
 * @param {string} apiKey API key
 * @param {string} apiSecret API key secret
 * @param {function} onSuccess Callback function to call on success
 * @param {function} onError Callback function to call on failure
 */
export function sendApiKeySaveRequest(apiKey, apiSecret, onSuccess, onError) {
  const user = getAuthenticatedUser();
  if (!user) throw new Error("User must be authenticated to save API key");
  const username = user.username;
  const postData = {
    apiKey: apiKey,
    apiSecret: apiSecret,
  };
  sendApiPostRequest(
    `/users/${username}/api-key`,
    onSuccess,
    postData,
    onError
  );
}

/**
 * Fetch Exchange-API key from the backend API
 * @return {Promise<string>} API key, as a single string
 * Throws and exception on error
 */
export async function fetchKeyFromApi() {
  const user = getAuthenticatedUser();
  if (!user) throw new Error("User must be authenticated to fetch API key");
  const username = user.username;
  return asyncApiGet(`/users/${username}/api-key`);
}

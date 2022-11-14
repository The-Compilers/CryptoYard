import {
  asyncApiGet,
  sendApiDeleteRequest,
  sendApiPostRequest,
} from "./requests";
import { getAuthenticatedUser } from "./authentication";

//////////////////////////////////////
// API requests to the backend
//////////////////////////////////////

/**
 * Send request to API - delete user account
 * @param {string} username Username of the user to delete
 * @param {string} password Password of the user to delete
 * @param {function} onSuccess Callback function to call on success
 * @param {function} onError Callback function to call on failure
 */
export function sendUserDeleteRequest(username, password, onSuccess, onError) {
  const postData = {
    username: username,
    password: password,
  };
  sendApiDeleteRequest("/close-account", onSuccess, postData, onError);
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
 * Fetch API key from the server
 */
export async function fetchApiKey() {
  const user = getAuthenticatedUser();
  if (!user) throw new Error("User must be authenticated to save API key");
  const username = user.username;
  return await asyncApiGet(`/usersz/${username}/api-key`);
}

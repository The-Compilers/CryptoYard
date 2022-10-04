import {sendApiDeleteRequest} from "./requests";

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
    "username": username,
    "password": password
  };
  sendApiDeleteRequest("/close-account", onSuccess, postData, onError);

}

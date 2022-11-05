// Authentication stuff

import { sendApiPostRequest } from "./requests";
import { deleteCookie, getCookie, setCookie } from "./cookies";

/**
 * Get the currently authenticated user
 * @returns User object or null if user is not authenticated
 */
export function getAuthenticatedUser() {
  let user = null;
  const username = getCookie("current_username");
  const commaSeparatedRoles = getCookie("current_user_roles");
  if (username && commaSeparatedRoles) {
    const roles = commaSeparatedRoles.split(",");
    user = {
      "username": username,
      "firstName": "Adam", // TODO - store those in JWT? Or simply remove them
      "lastName": "Jensen",
      "roles": roles
    };
  }
  return user;
}

/**
 * Check if the given user has admin rights
 * @param user
 * @returns {boolean}
 */
export function isAdmin(user) {
  return user && user.roles && user.roles.includes("ROLE_ADMIN");
}

/**
 * Send authentication request to the API
 * @param username Username
 * @param password Password, plain text
 * @param successCallback Function to call on success
 * @param errorCallback Function to call on error, with error code and response text as parameters
 */
export function sendAuthenticationRequest(username, password, successCallback, errorCallback) {
  const postData = {
    "username": username,
    "password": password
  };
  sendApiPostRequest(
    "/authenticate",
    (jwtResponse) => onAuthSuccess(jwtResponse, successCallback),
    postData,
    errorCallback
  );
}

/**
 * Send a SignUp request
 * @param {string} username
 * @param {string} email
 * @param {string} password
 * @param {string} repeatPassword
 * @param {function} successCallback Function to call on success
 * @param {function} errorCallback Function to call on error, with error code and response text as parameters
 */
export function sendSignUpRequest(username, email, password, repeatPassword, successCallback, errorCallback) {
  const postData = {
    "username": username,
    "email": email,
    "password": password,
    "repeatedPassword": repeatPassword
  };
  sendApiPostRequest("/signup", (jwtResponse) => onAuthSuccess(jwtResponse, successCallback),
    postData, errorCallback);
}


/**
 * Function called when authentication has been successful and JWT is received from the API
 * @param {object} jwtResponse The HTTP response from the API, contains an object with `jwt` property
 * @property jwt JWT token, as a string
 * @param {function} callback A callback function provided by the invoker, to be called at the end
 */
function onAuthSuccess(jwtResponse, callback) {
  setCookie("jwt", jwtResponse.jwt);
  const userData = parseJwtUser(jwtResponse.jwt);
  if (userData) {
    setCookie("current_username", userData.username);
    setCookie("current_user_roles", userData.roles.join(","));
    callback(userData);
  }
}

/**
 * Parse JWT string, extract information from it
 * Code copied from https://stackoverflow.com/questions/38552003/how-to-decode-jwt-token-in-javascript-without-using-a-library
 * @param token JWT token string
 * @returns {any} Decoded JWT object
 */
function parseJwt(token) {
  const base64Url = token.split(".")[1];
  const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
  const jsonPayload = decodeURIComponent(atob(base64).split("").map(function(c) {
    return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
  }).join(""));

  return JSON.parse(jsonPayload);
}

/**
 * Parse JWT string, extract a User object
 * @param jwtString
 * @return User object
 */
function parseJwtUser(jwtString) {
  let user = null;
  const jwtObject = parseJwt(jwtString);
  if (jwtObject) {
    user = {
      "username": jwtObject.sub,
      "roles": jwtObject.roles.map(r => r.authority)
    };
  }
  return user;
}


/**
 * Delete all cookies related to authorization (user session)
 */
export function deleteAuthorizationCookies() {
  deleteCookie("jwt");
  deleteCookie("current_username");
  deleteCookie("current_user_roles");
}

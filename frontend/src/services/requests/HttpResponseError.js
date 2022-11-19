/**
 * An exception to be used for HTTP error responses
 */
export class HttpResponseError implements Error {
  constructor(statusCode, message) {
    this.statusCode = statusCode;
    this.message = message;
  }

  /**
   * @return {int} The HTTP error code (401 unauthorized, etc)
   */
  getErrorCode() {
    return this.statusCode;
  }
}

import { useState } from "react";
import Dialog from "@mui/material/Dialog";
import {
  Button,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  TextField,
} from "@mui/material";
import { getAuthenticatedUser } from "../../services/authentication";
import { apiDeleteUser } from "../../services/api";
import { MessageSnackbar } from "../snackbar/MessageSnackbar";

/**
 * A dialog window for user deletion. Asks to enter the password and confirm
 * the action.
 * @param {bool} visible When true, the dialog is visible; hidden when false
 * @param {function} setVisible A function which can toggle the visibility
 * @param {function} setDeleting A function which can toggle a "deleting status"
 * @param {function} doLogout A function to call to force log-out
 * @return {JSX.Element}
 * @constructor
 */
export function AccountDeleteDialog({
  visible,
  setVisible,
  setDeleting,
  doLogout,
}) {
  const [passwordOK, setPasswordOK] = useState(false);
  const [apiError, setApiError] = useState(null);
  const [snackbarVisible, setSnackbarVisible] = useState(false);

  return (
    <>
      <Dialog
        open={visible}
        onClose={() => setVisible(false)}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          {"Do you really want to delete your account?"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            If you delete your account, all data will be lost. You can't undo
            this action!
          </DialogContentText>
          <p>Enter your password to confirm the operation.</p>
          <TextField
            type="password"
            label="Your password"
            variant="standard"
            id="password_field"
            onChange={checkPassword}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setVisible(false)} autoFocus>
            Cancel
          </Button>
          <Button
            onClick={sendDeleteRequest}
            disabled={!passwordOK}
            variant="contained"
          >
            Delete my account
          </Button>
        </DialogActions>
      </Dialog>

      {/* The snackbar will be shown only when necessary - when the user-delete 
      operation is complete */}
      <MessageSnackbar
        visible={snackbarVisible}
        onClose={closeSnackbarAndLogout}
        message={apiError ? apiError : "User deleted"}
      />
    </>
  );

  /**
   * Check whether the current password can be accepted (satisfies minimum requirements)
   * @param event The update-event from the password input field
   */
  function checkPassword(event) {
    const password = event.target.value;
    setPasswordOK(password !== "" && password.length >= 6);
  }

  /**
   * Send API request to user account deletion endpoint
   */
  function sendDeleteRequest() {
    setDeleting(true);
    setVisible(false);
    const username = getAuthenticatedUser().username;
    const password = document.getElementById("password_field").value;
    apiDeleteUser(username, password)
      .then(() => onDeleteResponse(null))
      .catch((error) => onDeleteResponse(error));
  }

  /**
   * This function is called when the response from the user deletion API endpoint comes
   * @param {HttpResponseError} error Non-null when an error has happened
   */
  function onDeleteResponse(error) {
    if (error) {
      setApiError(error.message);
    }
    setSnackbarVisible(true);
    setDeleting(false);
  }

  function closeSnackbarAndLogout() {
    setSnackbarVisible(false);
    if (!apiError) {
      doLogout();
    }
  }
}

import { useState } from "react";
import {
  Button,
  CircularProgress,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  IconButton,
  Snackbar,
  TextField,
} from "@mui/material";
import Dialog from "@mui/material/Dialog";
import CloseIcon from "@mui/icons-material/Close";
import { getAuthenticatedUser } from "../../services/authentication";
import { sendUserDeleteRequest } from "../../services/api";

/**
 * Component for controlling the user account (deleting it, etc.)
 * @param doLogout The function to call to Sign-out (after the user is deleted)
 * @return {JSX.Element}
 * @constructor
 */
export function AccountSettings({ doLogout }) {
  const [dialogVisible, setDialogVisible] = useState(false);
  const [snackbarVisible, setSnackbarVisible] = useState(false);
  const [deleting, setDeleting] = useState(false);
  const [userDeleted, setUserDeleted] = useState(false);
  const [passwordOK, setPasswordOK] = useState(false);

  return (
    <section>
      <h3>Account control</h3>
      {deleting ? (
        <CircularProgress />
      ) : (
        <button
          className="action-button"
          onClick={() => setDialogVisible(true)}
        >
          Delete account
        </button>
      )}

      {/* The dialog will be shown only when necessary - to ask for password and confirmation */}
      <Dialog
        open={dialogVisible}
        onClose={() => setDialogVisible(false)}
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
          <Button onClick={() => setDialogVisible(false)} autoFocus>
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

      {/* The snackbar will be shown only when necessary - when the user-delete operation is complete */}
      <Snackbar
        open={snackbarVisible}
        autoHideDuration={5000}
        message={userDeleted ? "User deleted" : "Failed to delete the user"}
        action={
          <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={closeSnackbarAndLogout}
          >
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        onClose={closeSnackbarAndLogout}
      />
    </section>
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
    setDialogVisible(false);
    const username = getAuthenticatedUser().username;
    const password = document.getElementById("password_field").value;
    sendUserDeleteRequest(username, password)
      .then(() => onDeleteResponse(true))
      .catch(() => onDeleteResponse(false));
  }

  /**
   * This function is called when the response from the user deletion API endpoint comes
   * @param {boolean} success True when the user is deleted, false on error
   */
  function onDeleteResponse(success) {
    console.log(`onDeleteResponse(${success})`);
    setUserDeleted(success);
    setSnackbarVisible(true);
    setDeleting(false);
  }

  function closeSnackbarAndLogout() {
    setSnackbarVisible(false);
    if (userDeleted) {
      doLogout();
    }
  }
}

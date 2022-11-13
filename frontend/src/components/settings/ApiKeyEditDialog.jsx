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
import { sendApiKeySaveRequest } from "../../services/api";

/**
 * Dialog window for editing API key
 * @param visible When true, the dialog is visible
 * @param setVisible Function to call to set the visibility property (stored in a parent)
 * @param setInProgress Function to call to set the "inProgress" property (stored in a parent)
 * @param inProgress When true, the API call is in progress
 * @param setApiKey Function to call to set the API key in the "parent store"
 * @return {JSX.Element}
 * @constructor
 */
export function ApiKeyEditDialog({
  visible,
  setVisible,
  setInProgress,
  inProgress,
  setApiKey,
}) {
  const API_KEY_LENGTH = 64;
  const API_SECRET_LENGTH = 64;

  const [apiKeyOk, setApiKeyOk] = useState(false);
  const [apiSecretOk, setApiSecretOk] = useState(false);

  const [error, setError] = useState("");

  return (
    <>
      <Dialog
        open={visible}
        onClose={() => setVisible(false)}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
        fullWidth={true}
      >
        <DialogTitle id="alert-dialog-title">New API key</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Enter your Binance API key and secret.
          </DialogContentText>
          <TextField
            type="text"
            label="Key"
            variant="standard"
            id="api_key_input"
            fullWidth={true}
            onChange={checkApiKeyFormat}
            disabled={inProgress}
          />
          <br />
          <TextField
            type="text"
            label="Secret"
            variant="standard"
            fullWidth={true}
            id="api_secret_input"
            onChange={checkApiSecretFormat}
            disabled={inProgress}
          />
          <p className="error">{error}</p>
        </DialogContent>
        <DialogActions>
          <Button onClick={close} autoFocus>
            Cancel
          </Button>
          <Button
            onClick={sendApiKeyToBackend}
            disabled={!apiKeyOk || !apiSecretOk || inProgress}
            variant="contained"
          >
            Save key
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );

  function close() {
    setVisible(false);
    setError("");
    setApiKeyOk(false);
    setApiSecretOk(false);
  }

  function checkApiKeyFormat(event) {
    const apiKey = event.target.value;
    const formatOK = apiKey !== "" && apiKey.length === API_KEY_LENGTH;
    setApiKeyOk(formatOK);
  }

  function checkApiSecretFormat(event) {
    const secret = event.target.value;
    const formatOK = secret !== "" && secret.length === API_SECRET_LENGTH;
    setApiSecretOk(formatOK);
  }

  function sendApiKeyToBackend() {
    setInProgress(true);
    setError("");
    const apiKey = document.getElementById("api_key_input").value;
    const apiSecret = document.getElementById("api_secret_input").value;
    sendApiKeySaveRequest(
      apiKey,
      apiSecret,
      () => onApiResponse(true, ""),
      (status, error) => onApiResponse(false, error)
    );
  }

  /**
   * This function is called when the response from the key-save API endpoint comes
   * @param {boolean} success True when the key is saved, false on error
   * @param {string} error Error message received from the server
   */
  function onApiResponse(success, error) {
    if (success) {
      setApiKey(document.getElementById("api_key_input").value);
      close();
    } else {
      setError(error);
    }
    setInProgress(false);
  }
}

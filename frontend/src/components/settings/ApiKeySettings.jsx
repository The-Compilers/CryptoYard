/**
 * Settings for configuring the API key
 * @return {JSX.Element}
 * @constructor
 */
import { useState } from "react";
import Dialog from "@mui/material/Dialog";
import {
  Button,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  IconButton,
  Snackbar,
  TextField,
} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";

export function ApiKeySettings() {
  // TODO - load the key from the API
  const [currentApiKey, setCurrentApiKey] = useState("SomeApiKey");

  const [dialogVisible, setDialogVisible] = useState(false);
  const [snackbarVisible, setSnackbarVisible] = useState(false);

  return (
    <section>
      <h3>API key configuration</h3>
      {currentApiKey ? (
        <p>Key: {currentApiKey}</p>
      ) : (
        <p>API key not configured</p>
      )}
      <button
        className="action-button action-button__wide"
        type="submit"
        onClick={() => setDialogVisible(true)}
      >
        Set new API key
      </button>

      {/* The dialog will be shown only when necessary - to ask for 
      API key and secret */}
      <KeyEditDialog visible={dialogVisible} setVisible={setDialogVisible} />

      {/* The snackbar will be shown only when necessary - when the API key
      is saved on the server */}
      <KeyEditSnackbar
        visible={snackbarVisible}
        setVisible={setSnackbarVisible}
      />
    </section>
  );
}

function KeyEditDialog({ visible, setVisible }) {
  const API_KEY_LENGTH = 64;
  const API_SECRET_LENGTH = 64;

  const [apiKeyOk, setApiKeyOk] = useState(false);
  const [apiSecretOk, setApiSecretOk] = useState(false);

  return (
    <Dialog
      open={visible}
      onClose={() => setVisible(false)}
      aria-labelledby="alert-dialog-title"
      aria-describedby="alert-dialog-description"
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
        />
        <br />
        <TextField
          type="text"
          label="Secret"
          variant="standard"
          fullWidth={true}
          id="api_secret_input"
          onChange={checkApiSecretFormat}
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={() => setVisible(false)} autoFocus>
          Cancel
        </Button>
        <Button
          onClick={saveApiKey}
          disabled={!apiKeyOk || !apiSecretOk}
          variant="contained"
        >
          Save key
        </Button>
      </DialogActions>
    </Dialog>
  );

  function checkApiKeyFormat(event) {
    const apiKey = event.target.value;
    setApiKeyOk(apiKey !== "" && apiKey.length === API_KEY_LENGTH);
  }

  function checkApiSecretFormat(event) {
    const apiSecret = event.target.value;
    setApiSecretOk(apiSecret !== "" && apiSecret.length === API_SECRET_LENGTH);
  }

  function saveApiKey() {
    console.log("Saving key...");
    // TODO - send API request to save the key
  }
}

function KeyEditSnackbar({ visible, setVisible }) {
  const [successfullySaved, setSuccessfullySaved] = useState(false);

  return (
    <Snackbar
      open={visible}
      autoHideDuration={5000}
      message={successfullySaved ? "API key saved" : "Failed to save the key"}
      action={
        <IconButton
          size="small"
          aria-label="close"
          color="inherit"
          onClick={() => setVisible(false)}
        >
          <CloseIcon fontSize="small" />
        </IconButton>
      }
      onClose={() => setVisible(false)}
    />
  );
}

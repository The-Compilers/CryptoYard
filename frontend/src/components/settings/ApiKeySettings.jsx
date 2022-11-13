/**
 * Settings for configuring the API key
 * @return {JSX.Element}
 * @constructor
 */
import { useState } from "react";
import { CircularProgress } from "@mui/material";
import { ApiKeyEditDialog } from "./ApiKeyEditDialog";

export function ApiKeySettings() {
  // TODO - load the key from the API
  const [currentApiKey, setCurrentApiKey] = useState("SomeApiKey");

  const [dialogVisible, setDialogVisible] = useState(false);
  const [apiCallInProgress, setApiCallInProgress] = useState(false);

  return (
    <section>
      <h3>API key configuration</h3>
      {currentApiKey ? (
        <p>Key: {currentApiKey}</p>
      ) : (
        <p>API key not configured</p>
      )}
      {!apiCallInProgress ? (
        <button
          className="action-button action-button__wide"
          type="submit"
          onClick={() => setDialogVisible(true)}
        >
          Set new API key
        </button>
      ) : (
        <CircularProgress />
      )}

      {/* The dialog will be shown only when necessary - to ask for 
      API key and secret */}
      <ApiKeyEditDialog
        visible={dialogVisible}
        setVisible={setDialogVisible}
        inProgress={apiCallInProgress}
        setInProgress={setApiCallInProgress}
        setApiKey={setCurrentApiKey}
      />
    </section>
  );
}

/**
 * Settings for configuring the API key
 * @return {JSX.Element}
 * @constructor
 */
import { useState } from "react";
import { CircularProgress } from "@mui/material";
import { ApiKeyEditDialog } from "./ApiKeyEditDialog";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { apiDeleteApiKey, apiFetchApiKey } from "../../services/api";
import { SmallHorizontalSpacing } from "../layout/SmallHorizontalSpacing";
import { MessageSnackbar } from "../snackbar/MessageSnackbar";
import { ActionButton } from "../common/ActionButton";

/**
 * Component showing API Key settings section
 * @return {JSX.Element}
 * @constructor
 */
export function ApiKeySettings() {
  const [dialogVisible, setDialogVisible] = useState(false);
  const [apiCallInProgress, setApiCallInProgress] = useState(false);
  const [currentApiKey, setCurrentApiKey] = useState("");
  const [snackbarVisible, setSnackbarVisible] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const apiKeyExists = !!currentApiKey;

  const { isLoading, isError, isSuccess } = useQuery({
    queryKey: ["api_key"],
    queryFn: apiFetchApiKey,
    onSuccess: (data) => setCurrentApiKey(data),
  });
  const queryClient = useQueryClient();

  return (
    <section>
      <h3>API key configuration</h3>
      <p>
        {isLoading
          ? "Loading..."
          : isError
          ? "Could not fetch API key!"
          : isSuccess && currentApiKey
          ? `Api key: ${currentApiKey}`
          : ""}
      </p>
      {!apiCallInProgress && !isLoading ? (
        <>
          <ActionButton
            onClick={() => setDialogVisible(true)}
            text={apiKeyExists ? "Update" : "Add"}
          />
          <SmallHorizontalSpacing />
          {apiKeyExists ? (
            <ActionButton onClick={deleteApiKey} text="Delete" />
          ) : (
            ""
          )}
        </>
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

      <MessageSnackbar
        message={snackbarMessage}
        visible={snackbarVisible}
        onClose={() => setSnackbarVisible(false)}
      />
    </section>
  );

  /**
   * Send a request to the API to delete the API key, handle the response
   */
  function deleteApiKey() {
    setApiCallInProgress(true);
    apiDeleteApiKey()
      .then((responseText) => {
        showSnackbar("Key deleted");
        queryClient.invalidateQueries({ queryKey: "api_key" });
      })
      .catch((error) => {
        showSnackbar(error.message);
      })
      .finally(() => {
        setApiCallInProgress(false);
      });
  }

  function showSnackbar(message) {
    setSnackbarVisible(true);
    setSnackbarMessage(message);
  }
}

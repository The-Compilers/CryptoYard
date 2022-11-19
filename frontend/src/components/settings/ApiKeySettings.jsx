/**
 * Settings for configuring the API key
 * @return {JSX.Element}
 * @constructor
 */
import { useState } from "react";
import { CircularProgress } from "@mui/material";
import { ApiKeyEditDialog } from "./ApiKeyEditDialog";
import { useQuery } from "@tanstack/react-query";
import { fetchKeyFromApi } from "../../services/api";

/**
 * Component showing API Key settings section
 * @return {JSX.Element}
 * @constructor
 */
export function ApiKeySettings() {
  const [dialogVisible, setDialogVisible] = useState(false);
  const [apiCallInProgress, setApiCallInProgress] = useState(false);
  const [currentApiKey, setCurrentApiKey] = useState("");

  const { isLoading, isError, isSuccess } = useQuery({
    queryKey: ["api_key"],
    queryFn: fetchKeyFromApi,
    onSuccess: (data) => setCurrentApiKey(data),
  });

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
          : "API key not configured"}
      </p>
      {!apiCallInProgress && !isLoading ? (
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

/**
 * Settings for configuring the API key
 * @return {JSX.Element}
 * @constructor
 */
import { useState } from "react";
import { CircularProgress } from "@mui/material";
import { ApiKeyEditDialog } from "./ApiKeyEditDialog";
import { useQuery } from "@tanstack/react-query";
import { fetchApiKey } from "../../services/api";

export function ApiKeySettings() {
  const [dialogVisible, setDialogVisible] = useState(false);
  const [apiCallInProgress, setApiCallInProgress] = useState(false);
  //const [currentApiKey, setCurrentApiKey] = useState("");

  const query = useQuery({
    queryKey: ["api_key"],
    queryFn: fetchApiKey,
  });

  return (
    <section>
      <h3>API key configuration</h3>
      {query.isSuccess ? (
        <p>Key: {query.data}</p>
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
      {query.isLoading ? "Loading..." :
        query.isError ? "Error:" + query.error : }
      { ""}

      {/* The dialog will be shown only when necessary - to ask for 
      API key and secret */}
      <ApiKeyEditDialog
        visible={dialogVisible}
        setVisible={setDialogVisible}
        inProgress={apiCallInProgress}
        setInProgress={setApiCallInProgress}
        // setApiKey={setCurrentApiKey}
      />
    </section>
  );
}

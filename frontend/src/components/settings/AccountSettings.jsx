import { useState } from "react";
import { CircularProgress } from "@mui/material";
import { AccountDeleteDialog } from "./AccoundDeleteDialog";

/**
 * Component for controlling the user account (deleting it, etc.)
 * @param doLogout The function to call to Sign-out (after the user is deleted)
 * @return {JSX.Element}
 * @constructor
 */
export function AccountSettings({ doLogout }) {
  const [dialogVisible, setDialogVisible] = useState(false);
  const [deleting, setDeleting] = useState(false);

  return (
    <section>
      <h3>Account control</h3>
      {deleting ? (
        <CircularProgress />
      ) : (
        <button
          className="action-button"
          onClick={() => setDialogVisible(true)}
          disabled={deleting}
        >
          Delete account
        </button>
      )}

      {/* The dialog will be shown only when necessary - to ask for password
       and confirmation */}
      <AccountDeleteDialog
        visible={dialogVisible}
        setVisible={setDialogVisible}
        setDeleting={setDeleting}
        doLogout={doLogout}
      />
    </section>
  );
}

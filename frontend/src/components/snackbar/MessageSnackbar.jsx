import { IconButton, Snackbar } from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";

/**
 * A snackbar to show a message and call a callback when closed
 * @param {bool} visible When true, the snackbar is displayed
 * @param {string} message The message to show.
 * @param {function} onClose Callback function to call when the snackbar is closed.
 * @return {JSX.Element}
 * @constructor
 */
export function MessageSnackbar({ visible, message, onClose }) {
  return (
    <Snackbar
      open={visible}
      autoHideDuration={5000}
      message={message}
      action={
        <IconButton
          size="small"
          aria-label="close"
          color="inherit"
          onClick={onClose}
        >
          <CloseIcon fontSize="small" />
        </IconButton>
      }
      onClose={onClose}
    />
  );
}

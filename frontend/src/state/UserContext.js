import { createContext } from "react";

/**
 * Store the logged-in user in a globally available context
 * @type {React.Context<object>}
 */
export const UserContext = createContext(null);

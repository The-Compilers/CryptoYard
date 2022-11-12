import { AccountSettings } from "../../components/nav/components/AccountSettings";

/**
 * The settings page
 * @param doLogout The function to call when Sign-out is needed
 * @return {JSX.Element}
 * @constructor
 */
export default function Settings({ doLogout }) {
  return (
    <main>
      <div className="box">
        <h2>Settings</h2>
        <AccountSettings doLogout={doLogout} />
      </div>
    </main>
  );

}

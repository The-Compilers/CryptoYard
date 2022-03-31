import { useParams } from "react-router-dom";

import Nav from "../../components/nav/Nav";

function Dashboard() {
  const { username } = useParams();

  return <Nav username={username} />;
}

export default Dashboard;

import { useParams } from "react-router-dom";

import "./dashboard.css";

import Nav from "../../components/nav/Nav";
import Table from "../../components/table/Table";

function Dashboard() {
  const { username } = useParams();

  return (
    <>
      <Nav username={username} />
      <main className="main">
        <Table />
      </main>
    </>
  );
}

export default Dashboard;

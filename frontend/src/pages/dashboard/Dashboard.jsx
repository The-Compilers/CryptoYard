import { useParams } from "react-router-dom";

import "./dashboard.css";

import Nav from "../../components/nav/Nav";
import Table from "../../components/table/Table";
import ChartBox from "../../components/chart/ChartBox";

function Dashboard() {
  const { username } = useParams();

  return (
    <>
      <Nav username={username} />
      <main className="main">
        <Table />
        <ChartBox />
      </main>
    </>
  );
}

export default Dashboard;

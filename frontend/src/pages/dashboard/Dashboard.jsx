import { useParams } from "react-router-dom";

import "./dashboard.css";

import Nav from "../../components/nav/Nav";
import Footer from "../../components/footer/Footer";
import Table from "../../components/table/Table";

function Dashboard() {
  const { username } = useParams();

  return (
    <>
      <Nav username={username} />
      <main className="main">
        <Table className="table" />
      </main>
      <Footer />
    </>
  );
}

export default Dashboard;

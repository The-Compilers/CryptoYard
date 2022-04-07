import React from "react";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Line } from "react-chartjs-2";
// This is just used to simulate random data. Later we want to fetch data from our api
// and display that.
import faker from "faker";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

export const options = {
  responsive: true,
  plugins: {
    legend: {
      position: "top",
    },
    title: {
      display: true,
      text: "Crypto Graph",
    },
  },
};

const labels = Array.from(Array(31).keys()).slice(1);

export const data = {
  labels,
  datasets: [
    {
      label: "Fake crypto currency",
      data: labels.map(() => faker.datatype.number({ min: 0, max: 50000 })),
      // TODO: Should try and get color from css file
      borderColor: "rgb(35, 215, 146)",
      backgroundColor: "rgb(35, 215, 146)",
    },
  ],
};

export default function Chart() {
  return <Line options={options} data={data} />;
}

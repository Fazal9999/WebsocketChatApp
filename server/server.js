const SocketServer = require("websocket").server;
const http = require("http");

const server = http.createServer((req, res) => {});
var port = Number(process.argv.slice(2)[0]);
server.listen(port, () => {
  console.log(`Server Running on http://localhost:${port}`);
});

wsServer = new SocketServer({ httpServer: server });

const connections = [];

wsServer.on("request", (req) => {
  const connection = req.accept();
  console.log("new client connected");
  connections.push(connection);

  connection.on("message", (mes) => {
    connections.forEach((element) => {
      if (element != connection) element.sendUTF(mes.utf8Data);
    });
  });

  connection.on("close", (resCode, des) => {
    console.log("connection closed");
    connections.splice(connections.indexOf(connection), 1);
  });
});

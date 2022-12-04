import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import NavBar from "./components/NavBar";
import { ThemeProvider, createTheme } from "@mui/material/styles";

const theme = createTheme({
  palette: {
    primary: {
      main: '#454545',
      dark: '#000000',
      contrastText: '#fff',
    }
  },
});

function App() {
  return (
    <BrowserRouter>
      <ThemeProvider theme={theme}>
        <NavBar/>
          <Routes>
            <Route path="/" element={<Home />} />
          </Routes>
      </ThemeProvider>
    </BrowserRouter>
  
  );
}

export default App;
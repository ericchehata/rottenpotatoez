import * as React from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import {
  AppBar,
  Box,
  Container,
  SvgIcon,
  IconButton,
  Menu,
  MenuItem,
  Toolbar,
  Typography,
} from "@mui/material";
import {
  AccountCircle,
} from "@mui/icons-material";
import NavBarStyle from "../styles/NavBarStyle";
import {ReactComponent as Logo} from "../icons/logo.svg";

function NavBar() {
  const [anchorElAccountMenu, setAnchorElAccountMenu] = React.useState(null);
  const [isAuthenticated, setIsAuthenticated] = React.useState(false);
  const [isAdmin, setIsAdmin] = React.useState(false);

  React.useEffect(() => {
    const username = localStorage.getItem("username");
    if (username) {
      setIsAuthenticated(true);
      axios.get(`users/${username}`).then((res) => {
        setIsAdmin(res.data.admin);
      });
    }
    // eslint-disable-next-line
  }, []);


  const handleAccountMenu = (event) => {
    setAnchorElAccountMenu(event.currentTarget);
  };

  const handleAccountMenuClose = (page) => {
    setAnchorElAccountMenu(null);
    if(page.name === "Logout"){
      localStorage.removeItem("username");
      setIsAuthenticated(false);
      setIsAdmin(false);
    }
  };

  const accountMenuPages = isAuthenticated
    ? [
        { name: "Profile", link: "/profile" },
        { name: "My reviews", link: "/my-reviews" },
        { name: "Logout", link: "/signin" },
      ]
    : [
        { name: "Sign up", link: "/signup" },
        { name: "Sign in", link: "/signin" },
      ];

  if(isAdmin) accountMenuPages.splice(0, 0, { name: "Add movie", link: "/add-movie" });

  return (
    <AppBar position="static" color="primary">
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <Box sx={NavBarStyle.menuContainer}/>
          <SvgIcon component={Logo} sx={{mt:4, height:"60px", width:"60px"}} viewBox="0 0 100 100" />
          <Typography
            variant="h5"
            noWrap
            component="a"
            href="/"
            sx={NavBarStyle.title}
          >
            Rotten Potatoez
          </Typography>
          <div>
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleAccountMenu}
              color="inherit"
            >
              <AccountCircle />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElAccountMenu}
              anchorOrigin={{
                vertical: "bottom",
                horizontal: "right",
              }}
              keepMounted
              transformOrigin={{
                vertical: "top",
                horizontal: "right",
              }}
              open={Boolean(anchorElAccountMenu)}
              onClose={handleAccountMenuClose}
            >
              {accountMenuPages.map((page) => (
                <MenuItem
                  component={Link}
                  to={page.link}
                  key={page.name}
                  onClick={() => handleAccountMenuClose(page)}
                >
                  <Typography textAlign="center">{page.name}</Typography>
                </MenuItem>
              ))}
            </Menu>
          </div>
        </Toolbar>
      </Container>
    </AppBar>
  );
}
export default NavBar;
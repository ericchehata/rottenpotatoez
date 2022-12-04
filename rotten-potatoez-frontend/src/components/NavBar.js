import * as React from "react";
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
import { AccountCircle, Menu as MenuIcon } from "@mui/icons-material";
import NavBarStyle from "../styles/NavBarStyle";
import {ReactComponent as Logo} from "../icons/logo.svg";

function NavBar() {
  const [anchorElNav, setAnchorElNav] = React.useState(null);
  const [anchorElAccountMenu, setAnchorElAccountMenu] = React.useState(null);
<<<<<<< Updated upstream
=======
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
>>>>>>> Stashed changes

  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleAccountMenu = (event) => {
    setAnchorElAccountMenu(event.currentTarget);
  };

  const handleAccountMenuClose = () => {
    setAnchorElAccountMenu(null);
<<<<<<< Updated upstream
=======
    if (page.name === "Logout") {
      localStorage.removeItem("username");
      setIsAuthenticated(false);
      setIsAdmin(false);
    }
>>>>>>> Stashed changes
  };

  const pages = [{ name: "Add movie", link: "/add-movie" }];

  const accountMenuPages = [
    { name: "Profile", link: "/profile" },
    { name: "Sign up", link: "/signup" },
    { name: "Sign in", link: "/signin" },
  ];

  return (
<<<<<<< Updated upstream
      <AppBar position="static" color="primary">
        <Container maxWidth="xl">
          <Toolbar disableGutters>
            <Box sx={NavBarStyle.menuContainer}>
              <IconButton
                size="large"
                aria-label="account of current user"
                aria-controls="menu-appbar"
                aria-haspopup="true"
                onClick={handleOpenNavMenu}
                color="inherit"
              >
                <MenuIcon />
              </IconButton>
              <Menu
                id="menu-appbar"
                anchorEl={anchorElNav}
                anchorOrigin={{
                  vertical: "bottom",
                  horizontal: "left",
                }}
                keepMounted
                transformOrigin={{
                  vertical: "top",
                  horizontal: "left",
                }}
                open={Boolean(anchorElNav)}
                onClose={handleCloseNavMenu}
                sx={NavBarStyle.menu}
              >
                {pages.map((page) => (
                  <MenuItem
                    component={Link}
                    to={page.link}
                    key={page.name}
                    onClick={handleCloseNavMenu}
                  >
                    <Typography textAlign="center">{page.name}</Typography>
                  </MenuItem>
                ))}
              </Menu>
            </Box>
            <ThumbsUpDownIcon
              sx={NavBarStyle.logo}
            />
            <Typography
              variant="h5"
              noWrap
              component="a"
              href="/"
              sx={NavBarStyle.title}
=======
    <AppBar position="static" color="primary">
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <Box sx={NavBarStyle.menuContainer}>
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleOpenNavMenu}
              color="inherit"
            >
              <MenuIcon />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElNav}
              anchorOrigin={{
                vertical: "bottom",
                horizontal: "left",
              }}
              keepMounted
              transformOrigin={{
                vertical: "top",
                horizontal: "left",
              }}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
              sx={NavBarStyle.menu}
            >
              {pages.map((page) => (
                <MenuItem
                  component={Link}
                  to={page.link}
                  key={page.name}
                  onClick={handleCloseNavMenu}
                >
                  <Typography textAlign="center">{page.name}</Typography>
                </MenuItem>
              ))}
            </Menu>
          </Box>
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
>>>>>>> Stashed changes
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
                    onClick={handleAccountMenuClose}
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

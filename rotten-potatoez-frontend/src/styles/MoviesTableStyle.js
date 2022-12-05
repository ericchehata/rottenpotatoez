const MoviesTableStyle = {
    container: { 
      width: "90%", 
      mt: "3rem",
      mb: "3rem",
      mr: "5%",
      ml: "5%",
    },
    table: {
      minWidth: 650,
    },
    tableRow: {
      "&:last-child td, &:last-child th": { 
        border: 0 
      }
    },
    tableHeaderCell: {
      fontWeight: 700,
    },
    loadingContainer: {
      display: "flex",
      justifyContent: "center",
      alignItems: "center",
      height: "75vh",
    },
    search:{
      width: "50%",
      mr: "25%",
      ml: "25%",
      pt: "3rem",
    }
}

export default MoviesTableStyle;
import { Box, Grid } from "@mui/material";
import AnalyticsCard from "./AnalyticsCard";
import * as React from "react";
import Analytics from "./Analytics";



const AnalyticGraphs = () => {
    return (
        <Grid container rowSpacing={1} columnSpacing={{ xs: 1, sm: 2 }}>
            <Grid item xs={6}>
                <Box>
                    {["https://blogs.sas.com/content/iml/files/2016/09/barchartcurve1.png"].map((text) => (
                        <AnalyticsCard img={text} key={text} />
                    ))}
                </Box>

            </Grid>
            <Grid item xs={6}>
                <Box>
                    {["https://images.edrawsoft.com/articles/create-pie-chart/blank-pie-chart.png"].map((text) => (
                        <AnalyticsCard img={text} key={text} />
                    ))}
                </Box>
            </Grid>
        </Grid>)

}

export default AnalyticGraphs
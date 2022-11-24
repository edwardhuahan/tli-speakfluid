import React, { useEffect, useState } from 'react';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import TranscriptMenu from '../../components/TranscriptsMenu';
import DataTable from '../../components/DataTable';
import Analytics from '../../components/Analytics';

const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));

const stylingGuide = {
    /* A grid container has a total width of 12 units. Grid items must share a total width of greaterthan/equalto 12 units
    xs={2} means this Grid item has a minimal (i.e. "xs" or extra small) width of 2 units,
    which is 2/12 = 16.66% of the total width of screen. 
    Hence, width in MUI is based on proportion out of 12! This makes the website responsive. */
    totalWidth: 12,
    menuWidth: 2
}


export default function AnalyticsBoard({ showMenu }: any) {

    const [dataTableId, setDataTableId] = useState("Transcript1") //change to first transcript later

    const handleChange = (newId: string) => {
        setDataTableId(newId)
    }
    return (
        <Grid container >
            <Grid item xs={stylingGuide.menuWidth} sx={{ overflow: 'auto', display: showMenu ? "flex" : "none" }}>
                <TranscriptMenu />
            </Grid>
            <Grid item xs={showMenu ? (stylingGuide.totalWidth - stylingGuide.menuWidth) / 2 : (stylingGuide.totalWidth - stylingGuide.menuWidth) / 2 + 1}
                sx={{ padding: '20px', backgroundColor: '#90CAF9' }}>
                <DataTable dataTableId={dataTableId} />
            </Grid>
            <Grid item xs={showMenu ? (stylingGuide.totalWidth - stylingGuide.menuWidth) / 2 : (stylingGuide.totalWidth - stylingGuide.menuWidth) / 2 + 1}>
                <Analytics />
            </Grid>

        </Grid>
    );
}




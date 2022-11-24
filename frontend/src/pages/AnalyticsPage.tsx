import React, { useEffect, useState } from 'react';
import { styled } from '@mui/material/styles';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import TranscriptMenu from '../components/TranscriptsMenu';
import DataTable from '../components/DataTable';
import Analytics from '../components/Analytics';

const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));

export default function AnalyticsPage({ showMenu }: any) {

    const [dataTableId, setDataTableId] = useState("Transcript1") //change to first transcript later

    const handleChange = (newId: string) => {
        setDataTableId(newId)
    }
    return (
        <Grid container >
            <Grid item xs={2} sx={{ overflow: 'auto', display: showMenu ? "flex" : "none" }}>
                <TranscriptMenu />
                {/* <TranscriptMenu handleChange={handleChange} /> */}
            </Grid>
            <Grid item xs={showMenu ? 5 : 6} sx={{ padding: '20px', backgroundColor: '#90CAF9' }}>
                <DataTable dataTableId={dataTableId} />
                {/* <DataTable /> */}
            </Grid>
            <Grid item xs={showMenu ? 5 : 6}>
                <Analytics />
            </Grid>

        </Grid>
    );
}




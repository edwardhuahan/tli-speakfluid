import React from 'react';
import { Link } from 'react-router-dom';

/***ASSETS***/
import chatBot from '../assets/cb.jpg'

/***COMPONENTS***/
import NavBar from '../components/NavBar';
import { Button } from '@mui/material';

/***PAGES***/

/***STYLES***/
import '../styles/Global.css'
import '../styles/Home.css';

/***TYPES***/

export default function Home() {
  return (
    <div className='pageWrapper'>
      <NavBar />
      <div className='section centerContent hero'>
        <div style={{display: 'flex', flexDirection: 'row'}}>
          <div style={{width: '50%'}}>
            <img src={chatBot} alt='Illustration of a chatbot' style={{width: '100%', margin: '30px'}} />
          </div>
          <div className='centerContent' style={{margin: '30px', display: 'flex', flexDirection: 'column', width: '50%'}}>
            <h1 className='header' style={{marginBottom: '30px'}}>Your 1-click solution for classifying chatbot sentiments.</h1>
            <h2 className='subHeader' style={{marginBottom: '30px'}}>
              No need to manually classify hundreds of messages anymore. Simply upload your transcript 
              and get classified sentiments in a matter of seconds.
            </h2>
            <Link to='/analytics' style={{textDecoration: 'none', alignSelf: 'flex-start'}}>
              <Button variant="contained" 
                      style={{backgroundColor: '#474af1', borderRadius: '20px'}} 
                      size='large'
                      >
                <h2 className='header'>Get Started</h2>
              </Button>
            </Link>
          </div>
        </div>
      </div>
      <div className='section'>
        
      </div>
    </div>
  )
}

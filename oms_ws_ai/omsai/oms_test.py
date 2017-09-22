'''
Created on Jun 20, 2017

@author: quark
'''

import pandas as pd
from sqlalchemy import create_engine
engine = create_engine('postgresql://ercot_reader:superReader324@psedatatrial.crkajpgzxx4n.us-east-1.redshift.amazonaws.com:5439/powerstoretrial')
df = pd.read_sql_query('SELECT count(*) FROM ref_15min;', engine)
print (df)
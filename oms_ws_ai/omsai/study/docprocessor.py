
from gensim.models import word2vec
import logging
import gensim
import nltk
from nltk.corpus import gutenberg
import pandas as pd
import pymysql
import pymysql.cursors
import numpy as np
import random
import tensorflow as tf
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
from sklearn.cluster import KMeans
import re
import string



# In[2]:

connection = pymysql.connect(host='72.177.234.240',                             
                            
                             user='ccg',
                             password='ccg',
                             db='ccg3'
                            )


# In[3]:

ccg_category=pd.read_sql('select * from ccgcategory',connection)


# In[4]:

ccg_category


# In[5]:

model =gensim.models.KeyedVectors.load_word2vec_format('../data/glove_50.txt')


# In[6]:

model.vocab


# In[7]:

def buildTKs(inputText):
    cleanText=re.sub(r'[^\x00-\x7F]+',' ', inputText)
    regex = re.compile('[%s]' % re.escape(string.punctuation)) 
    cleanText = regex.sub(u' ', cleanText)

    # first build the tokens
    l_tokens=word_tokenize(cleanText.lower())
    # clear filling stop words
    new_term_vector=[]
    for word in l_tokens:
        if not word in stopwords.words('english'):
            new_term_vector.append(word)
    return new_term_vector


# In[8]:

testary='12. Phase�III:�Test�and�Acceptance�'
#testary= unicode(testary, 'utf-8')
t=buildTKs(testary)


# In[9]:

print (t)


# In[10]:

content=ccg_category['categorycontent'][3]


# In[11]:

content


# In[12]:

cary=buildTKs(content)


# In[13]:

cary


# In[17]:

vlist=[]
for word in cary:
    try:
        v=model.word_vec(word)
        vlist.append(v)
    except:
        continue


# In[18]:

kmean=KMeans(n_clusters=15, random_state=0)


# In[19]:

kmean.fit(vlist)


# In[20]:

kmean.cluster_centers_


# In[21]:

for v in kmean.cluster_centers_:
    print (model.most_similar(positive=[v],topn=3))


# In[ ]:





# coding: utf-8

# In[40]:

from gensim.models import word2vec
import logging
import gensim
from nltk.corpus import gutenberg


# In[4]:

model =gensim.models.KeyedVectors.load_word2vec_format('../data/glove_50.txt')


# In[41]:

model.similarity('computer', 'network')


# In[48]:

v1=model.word_vec("security")


# In[50]:

v2=model.word_vec("computer")


# In[54]:

v3 =model.word_vec("encryption")


# In[43]:

model.similar_by_vector(vector=v1,topn=10)


# In[55]:

v4=(v2+v1+v3)


# In[57]:

model.similar_by_vector(vector=v3,topn=10)


# In[ ]:




# Melange Model Module
A graph-model implementation meant for dynamically managing the opengl context as but flatten relationships between any amount of
opengl objects. The model itself contains a series hashtables for fast iteration and look-ups, which is only possible
due to the enforced relation-ship limitations. There is only "requires" and "provides" with each node identified by a composite key,
which makes it far more performant as this distinction can be made up front, removing the need for any kind of graph traversal. 

Using reactive monitoring of individual nodes, the model can support any amount of runtime modifications with as little overhead as possible.

In its fullest extend, any GLModel will take any amount of "reducers" that seek to pre-compute and cache as many operations for a given
part of the models lifecycle as possible. However, this will require more information about each node than could possibly be provided through
primitives (if the handles and alias's where kept as ints and strings), leading to the formation of a plethora of type-aliases. 
An acceptable memory cost increase per node, given how much performance it could yield if the reducers are able to introspect each nodes
purpose and the users intent.
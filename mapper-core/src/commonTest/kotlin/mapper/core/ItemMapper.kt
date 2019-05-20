package jetbrains.datalore.mapper.core

import jetbrains.datalore.base.observable.transform.Transformers

internal open class ItemMapper(item: Item) : Mapper<Item, Item>(item, Item()) {
    private lateinit var mySimpleRole: SimpleRoleSynchronizer<Item, Item>

    override fun registerSynchronizers(conf: Mapper.SynchronizersConfiguration) {
        conf.add(Synchronizers.forObservableRole(
                this, source.observableChildren, target.observableChildren, createMapperFactory()))
        conf.add(Synchronizers.forObservableRole(
                this, source.transformedChildren, Transformers.identityList(),
                target.transformedChildren, createMapperFactory()))


        conf.add(Synchronizers.forSingleRole(
                this, source.singleChild, target.singleChild, createMapperFactory()))

        mySimpleRole = Synchronizers.forSimpleRole(
                this, source.children, target.children, createMapperFactory())
        conf.add(mySimpleRole)

        conf.add(Synchronizers.forPropsTwoWay(source.name, target.name))
    }

    fun refreshSimpleRole() {
        mySimpleRole.refresh()
    }

    protected open fun createMapperFactory(): MapperFactory<Item, Item> {
        return object : MapperFactory<Item, Item> {
            override fun createMapper(source: Item): Mapper<out Item, out Item> {
                return ItemMapper(source)
            }
        }
    }
}
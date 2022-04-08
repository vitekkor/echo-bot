package com.vitekkor.echobot.chatModules

import com.vitekkor.echobot.dto.Body
import com.vitekkor.echobot.services.VkApi
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.BeanFactoryAware
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.GenericBeanDefinition
import org.springframework.integration.channel.PublishSubscribeChannel
import org.springframework.messaging.Message
import java.util.concurrent.Executors
import javax.annotation.PostConstruct

abstract class ChatModule(
    protected val channelName: String
) : BeanFactoryAware {
    protected val vkApi = bean<VkApi>()

    private lateinit var beanFactory: BeanFactory

    abstract fun process(body: Body)

    private fun Message<*>.asBody(): Body = payload as Body

    @PostConstruct
    fun chatModuleFlow() {
        val beanDefinitionPubSubChannel = GenericBeanDefinition()
        beanDefinitionPubSubChannel.setInstanceSupplier {
            PublishSubscribeChannel(Executors.newCachedThreadPool())
        }
        beanDefinitionPubSubChannel.setBeanClass(PublishSubscribeChannel::class.java)
        (beanFactory as BeanDefinitionRegistry).registerBeanDefinition(channelName, beanDefinitionPubSubChannel)
        (beanFactory.getBean(channelName) as PublishSubscribeChannel).apply {
            subscribe { message -> process(message.asBody()) }
        }
    }

    private inline fun <reified T> bean() = lazy<T> { beanFactory.getBean(T::class.java) }

    override fun setBeanFactory(beanFactory: BeanFactory) {
        this.beanFactory = beanFactory
    }
}
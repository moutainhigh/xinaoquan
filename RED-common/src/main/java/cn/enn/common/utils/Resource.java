package cn.enn.common.utils;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.util.Assert;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

/**
 * @Author: tiantao
 * @Date: 2019/5/24 8:30 AM
 * @Version 1.0
 */
@XmlRootElement
public class Resource<T> extends ResourceSupport {

    private final T content;

    /**
     * Creates an empty {@link org.springframework.hateoas.Resource}.
     */
    Resource() {
        this.content = null;
    }

    /**
     * Creates a new {@link org.springframework.hateoas.Resource} with the given content and {@link Link}s (optional).
     *
     * @param content must not be {@literal null}.
     * @param links the links to add to the {@link org.springframework.hateoas.Resource}.
     */
    public Resource(T content, Link... links) {
        this(content, Arrays.asList(links));
    }

    /**
     * Creates a new {@link org.springframework.hateoas.Resource} with the given content and {@link Link}s.
     *
     * @param content must not be {@literal null}.
     * @param links the links to add to the {@link org.springframework.hateoas.Resource}.
     */
    public Resource(T content, Iterable<Link> links) {

        Assert.notNull(content, "Content must not be null!");
        //Assert.isTrue(!(content instanceof Collection), "Content must not be a collection! Use Resources instead!");
        this.content = content;
        this.add(links);
    }

    /**
     * Returns the underlying entity.
     *
     * @return the content
     */
    //@JsonUnwrapped
    @XmlAnyElement
    public T getContent() {
        return content;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.hateoas.ResourceSupport#toString()
     */
    @Override
    public String toString() {
        return String.format("Resource { content: %s, %s }", getContent(), super.toString());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.hateoas.ResourceSupport#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null ) {
            return false;
        }

        if (this.getClass() != obj.getClass()){
            return false;
        }



        Resource<?> that = (Resource<?>) obj;

        boolean contentEqual = this.content == null ? that.content == null : this.content.equals(that.content);
        return contentEqual ? super.equals(obj) : false;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.hateoas.ResourceSupport#hashCode()
     */
    @Override
    public int hashCode() {

        int result = super.hashCode();
        result += content == null ? 0 : 17 * content.hashCode();
        return result;
    }
}
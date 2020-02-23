/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package cn.enn.entity;

import lombok.Data;
import java.io.Serializable;


@Data
public class MapEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String key;
	private String value;

}
